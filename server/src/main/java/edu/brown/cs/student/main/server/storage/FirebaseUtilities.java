package edu.brown.cs.student.main.server.storage;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class FirebaseUtilities implements StorageInterface {

  public FirebaseUtilities() throws IOException {
    // Try loading from classpath
    InputStream serviceAccount =
        getClass().getClassLoader().getResourceAsStream("firebase_config.json");

    if (serviceAccount == null) {
      throw new FileNotFoundException("firebase_config.json not found in classpath");
    }

    System.out.println("Loading firebase_config.json from classpath");

    FirebaseOptions options =
        new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build();

    FirebaseApp.initializeApp(options);
    System.out.println("Loaded firebase_config.json with db url: ");
  }

  public List<Map<String, Object>> getCollection(String collection_id)
      throws InterruptedException, ExecutionException, IllegalArgumentException {
    if (collection_id == null) {
      throw new IllegalArgumentException("getCollection: collection_id cannot be null");
    }

    Firestore db = FirestoreClient.getFirestore();
    // 1: Make the data payload to add to your collection
    CollectionReference dataRef = db.collection(collection_id);
    // 2: Get user documents
    QuerySnapshot dataQuery = dataRef.get().get();

    // 3: Get data from document queries
    List<Map<String, Object>> data = new ArrayList<>();
    for (QueryDocumentSnapshot doc : dataQuery.getDocuments()) {
      data.add(doc.getData());
    }

    return data;
  }

  @Override
  public void addDocument(String collection_id, String doc_id, Map<String, Object> data)
      throws IllegalArgumentException {
    if (collection_id == null || doc_id == null || data == null) {
      throw new IllegalArgumentException(
          "addDocument: collection_id, doc_id, or data cannot be null");
    }

    Firestore db = FirestoreClient.getFirestore();
    CollectionReference collectionRef = db.collection(collection_id);

    collectionRef.document(doc_id).set(data);
  }

  @Override
  public void clearCollection(String collectionId) {
    Firestore db = FirestoreClient.getFirestore();
    CollectionReference collectionRef = db.collection(collectionId);

    try {
      // Get all documents in the collection
      ApiFuture<QuerySnapshot> future = collectionRef.get();
      List<QueryDocumentSnapshot> documents = future.get().getDocuments();

      // Delete each document
      for (QueryDocumentSnapshot document : documents) {
        document.getReference().delete();
      }

      System.out.println("Cleared collection: " + collectionId);
    } catch (InterruptedException | ExecutionException e) {
      System.err.println("Error clearing collection: " + collectionId);
      e.printStackTrace();
    }
  }

  @Override
  public void deleteDocument(String collection_id, String doc_id) {
    if (collection_id == null || doc_id == null) {
      throw new IllegalArgumentException("deleteDocument: collection_id or doc_id cannot be null");
    }

    Firestore db = FirestoreClient.getFirestore();
    DocumentReference docRef = db.collection(collection_id).document(doc_id);

    try {
      // Delete the specific document
      docRef.delete();
      System.out.println("Deleted document: " + doc_id + " from collection: " + collection_id);
    } catch (Exception e) {
      System.err.println("Error deleting document: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
