# AttendancePortal
Attendance System For Satsangis


# Business Rules
-User must be a member(i.e. memberId is must) but a member may or may not be a user i.e. user extends member
-Member can be upgraded to a user. At that time role must be given. userId will be auto generated, password can be set by user.
-user can be given option to update the user id once. A flag should be there to track the initialisation of custom user id.
-There should be an option to update the password of a user, forgot password, forgot userId.
-Give a search option which will display memberId, name and photo.
-found multiple users please provide member number to update the user first time.
-first time user must not update other's details.
-user registration provide user resource location link (user update path + UUID) and ask member to update user id and password
-if the user clicks to update then fetch all userId at front end so that entered id may be matched with stored id for uniqueness
-if user updates user id and password then delete default userId;


POST: take this data and apply it to the resource identified by the given URI, following the rules you documented for the resource media type.

PUT: replace whatever is identified by the given URI with this data, ignoring whatever is in there already, if anything.

PATCH: if the resource identified by the given URI still has the same state it had the last time I looked, apply this diff to it.

# Mapping
//member related
"MemberId" - MEMBER_ID
"FullName" - FULL_NAME
"Email" - EMAIL_ID

//user related
"User Id" - USER_ID
"Password" - PASSWORD

//presence related
"Date" - PRESENCE_DATE
