from django.http import HttpResponse
from django.urls import path


def home(request):
    return HttpResponse("Django + Celery + Redis is running")


urlpatterns = [
    path("", home),
]
