set -e
./gradlew clean test
./gradlew build
docker build -f  src/main/docker/Dockerfile.jvm -t localhost:32000/locus/odyssey-reports-centre:1.0 .
docker push localhost:32000/locus/odyssey-reports-centre:1.0
kubectl apply -f kubernetes.yml
kubectl patch deployment odyssey-reports-centre -p "{\"spec\":{\"template\":{\"metadata\":{\"labels\":{\"date\":\"`date +'%s'`\"}}}}}"