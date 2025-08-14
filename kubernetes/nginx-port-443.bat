kubectl wait --for=condition=ready pod -l app.kubernetes.io/component=controller -n ingress --timeout=120s
kubectl port-forward -n ingress svc/ingress-nginx-controller 443:443
