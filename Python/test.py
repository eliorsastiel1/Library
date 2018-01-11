import firebase_admin
from firebase_admin import credentials
from firebase_admin import db
from  firebase_admin import auth

import json



cred = credentials.Certificate('library-187413-firebase-adminsdk-jum3x-1faa04f55a.json')

firebase_admin.initialize_app(cred,
                                 { 'databaseURL': 'https://library-187413.firebaseio.com/',
                                 }
                                 )



def delete_user():
    email=raw_input("enter the email you want to delete: ")
    ref = db.reference('users')
    for key, value in ref.get().items():
        if value['email']==email:
            db.reference('users').child(key).delete()
            auth.delete_user(key)
    print("user deleted!")




def PrintAllUsers():
    ref = db.reference('users')
    print(json.dumps(ref.get(), indent=4, sort_keys=True))
    print ("All users printed")

def Creat_User():

    Email= raw_input("Email: ")
    Password=raw_input("Password: ")
    Firstname= raw_input("First Name: ")
    Lastname= raw_input("Last Name:")
    Address=raw_input("Address:")
    user=auth.create_user(email=Email, password=Password)
    id=user.uid

    ref = db.reference('admin')
    if(Email.__contains__("admin")):
        ref.child(id).set({
            'address': Address,
            'email': Email,
            'first name': Firstname,
            'last name': Lastname,
            'password': Password
        })
        print ("created a new user!")
    ref = db.reference('users')
    ref.child(id).set({
        'address': Address,
        'email': Email,
        'first name': Firstname,
        'last name': Lastname,
        'password': Password
    })
    print ("created a new user!")


def menu():
    while True:
        action = input('Hey! choose the action you want to do?:\n remove user by email(0),show all users(1),create a new user(2) ')
        if action == 0:
            delete_user()
        elif action == 1:
            PrintAllUsers()
        elif action == 2:
            Creat_User()
        else:
            break


menu()