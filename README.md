
Run the application and go to :
http://localhost:8080/swagger-ui.html#/invoice-controller
it will store metadata for the invoice in order to retrieve information of the document
and send a file of the invoice to s3  

**For local use**
use localstack to run aws locally
for more information : https://github.com/localstack/localstack
dont forget to configure aws credential

`aws configure`

and set access and secret key

**Docker**
Setup ECS in Docker
Type docker ecs setup
Enter context nam
Enter cluster name
Enter region
Enter Credentials
`docker context use {the name of your context }`

**Deploy our image to AWS Elastic Container Registry (ECR)**
Clone this repo from git 
Create an  ECR Repo format `repo-name/application-name`
build image on ECR repo as shown on aws console
that will generate an image uri
put it in docker-compose.yml
Deploy the application `docker ecs compose -n billing up`

enjoy!
