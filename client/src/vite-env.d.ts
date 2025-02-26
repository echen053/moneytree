interface ImportMetaEnv {
  VITE_APP_NODE_ENV: string;
  VITE_CLERK_PUBLISHABLE_KEY: string;
  VITE_MAPBOX_TOKEN: string;
  VITE_HOST: string;
}

interface ImportMeta {
  env: ImportMetaEnv;
}
