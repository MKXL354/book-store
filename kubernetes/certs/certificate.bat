openssl genrsa -out book-store.local.key 2048
openssl req -new -key book-store.local.key -out book-store.local.csr -subj "/CN=localhost/O=book-store"
openssl x509 -req -days 365 -in book-store.local.csr -signkey book-store.local.key -out book-store.local.crt
kubectl create secret tls book-store-tls --cert=book-store.local.crt --key=book-store.local.key -n book-store