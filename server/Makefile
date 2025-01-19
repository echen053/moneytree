# Variables
IMAGE_NAME = money_tree
TAG = latest
REGISTRY = unknown # Replace with GCR, ECR, or ACR URL
PROJECT_DIR = /server
DOCKERFILE = Dockerfile
PORT = 3232

# Default target
.PHONY: all
all: build

# Build the Docker image
.PHONY: build
build:
	docker build -t $(IMAGE_NAME):$(TAG) -f $(DOCKERFILE) .

# Run the Docker container locally
.PHONY: run
run:
	docker run -p $(PORT):$(PORT) $(IMAGE_NAME):$(TAG)

# Tag the Docker image for the registry
.PHONY: tag
tag:
	docker tag $(IMAGE_NAME):$(TAG) $(REGISTRY)/$(IMAGE_NAME):$(TAG)

# Push the Docker image to the registry
.PHONY: push
push: tag
	docker push $(REGISTRY)/$(IMAGE_NAME):$(TAG)

.PHONY: debug
debug:
	docker run -it money_tree:latest bash

# Clean up dangling images
.PHONY: clean
clean:
	docker image prune -f

# Help target to list all available commands
.PHONY: help
help:
	@echo "Available commands:"
	@echo "  make build     - Build the Docker image"
	@echo "  make run       - Run the Docker container locally"
	@echo "  make tag       - Tag the Docker image for the registry"
	@echo "  make push      - Push the Docker image to the registry"
	@echo "  make clean     - Remove unused Docker images"
	@echo "  make help      - Show this help message"