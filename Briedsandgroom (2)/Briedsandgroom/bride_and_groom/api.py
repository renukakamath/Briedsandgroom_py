from flask import *
from database import *

import demjson
import uuid


api=Blueprint('api',__name__)

@api.route('/login',methods=['get','post'])
def login():
	data={}
	
	username = request.args['username']
	password = request.args['password']
	q="SELECT * from login where username='%s' and password='%s'" % (username,password)
	res = select(q)
	if res :
		data['status']  = 'success'
		data['data'] = res
	else:
		data['status']	= 'failed'
	data['method']='login'
	return  demjson.encode(data)


@api.route('/userregister',methods=['get','post'])
def userregister():
	data={}

	uname = request.args['uname']
	passs = request.args['pass']
	fname = request.args['fname']
	lname = request.args['lname']
	email = request.args['email']
	place = request.args['place']
	land = request.args['land']
	phone = request.args['phone']
	hname = request.args['hname']
	pin = request.args['pin']

	q="insert into login values(null,'%s','%s','user')" %(uname,passs)
	id=insert(q)
	q="insert into users values(null,'%s','%s','%s','%s','%s','%s','%s','%s','%s')" %(id,fname,lname,hname,place,land,pin,phone,email) 
	insert(q)
	
	data['status']  = 'success'
	
	data['method']='userregister'
	return  demjson.encode(data)



@api.route('/Userviewdesiners',methods=['get','post'])
def Userviewdesiners():
	data={}
	
	q="SELECT * from designers"
	print(q)
	res=select(q)
	if res :
		data['status']  = 'success'
		data['data'] = res
	else:
		data['status']	= 'failed'
	data['method']='Userviewdesiners'
	return  demjson.encode(data)



@api.route('/Userviewdesigns',methods=['get','post'])
def Userviewdesigns():
	data={}
	did=request.args['did']
	q="SELECT * from designs inner join categories using(category_id) where designer_id='%s'" %(did)
	print(q)
	res=select(q)
	if res :
		data['status']  = 'success'
		data['data'] = res
	else:
		data['status']	= 'failed'
	data['method']='Userviewdesigns'
	return  demjson.encode(data)



@api.route('/userbookdesign',methods=['get','post'])
def userbookdesign():
	data={}

	did = request.args['did']
	lid = request.args['lid']
	

	q="insert into bookings values(null,(select user_id from users where login_id='%s'),'%s',curdate(),'designs','pending')" %(lid,did)
	id=insert(q)
	q="insert into payment values(null,'%s','book',curdate())" %(id)
	id=insert(q)
	
	
	data['status']  = 'success'
	
	data['method']='userbookdesign'
	return  demjson.encode(data)


@api.route('/usershareideas',methods=['get','post'])
def usershareideas():
	data={}
	did = request.args['did']
	title = request.args['title']
	desc = request.args['desc']
	budget = request.args['budget']
	date = request.args['date']
	lid = request.args['lid']
	

	q="insert into requests values(null,(select user_id from users where login_id='%s'),'%s','%s','%s','%s','%s',curdate())" %(lid,did,title,desc,budget,date)
	id=insert(q)
	
	data['status']  = 'success'
	
	data['method']='usershareideas'
	return  demjson.encode(data)


@api.route('/userreported',methods=['get','post'])
def userreported():
	data={}
	did = request.args['did']
	reason = request.args['reason']
	lid = request.args['lid']
	

	q="insert into reported values(null,(select user_id from users where login_id='%s'),'%s','%s',curdate())" %(lid,did,reason)
	id=insert(q)
	
	data['status']  = 'success'
	
	data['method']='userreported'
	return  demjson.encode(data)



@api.route('/user_view_rated',methods=['get','post'])
def user_view_rated():
	data={}
	did=request.args['did']
	lid=request.args['lid']
	q="SELECT * from ratings where designer_id='%s' and user_id=(select user_id from users where login_id='%s')" %(did,lid)
	print(q)
	res=select(q)
	if res :
		data['status']  = 'success'
		data['data'] = res[0]['rate']
		data['data1'] = res[0]['review']
	else:
		data['status']	= 'failed'
	data['method']='user_view_rated'
	return  demjson.encode(data)


@api.route('/user_rate_designer',methods=['get','post'])
def user_rate_designer():
	data={}
	did = request.args['did']
	rating = request.args['rating']
	review = request.args['review']
	lid = request.args['lid']
	
	q="select * from ratings where designer_id='%s' and user_id=(select user_id from users where login_id='%s')" %(did,lid)
	res=select(q)
	if not res:
		q="insert into ratings values(null,(select user_id from users where login_id='%s'),'%s','%s','%s',curdate())" %(lid,did,rating,review)
		id=insert(q)
	else:
		q="update ratings set rate='%s',review='%s' where designer_id='%s' and user_id=(select user_id from users where login_id='%s')" %(rating,review,did,lid)
		update(q)
	data['status']  = 'success'
	
	data['method']='user_rate_designer'
	return  demjson.encode(data)


@api.route('/userviewfeedback',methods=['get','post'])
def userviewfeedback():
	data={}
	did=request.args['did']
	lid=request.args['lid']
	q="SELECT * from feedback where designer_id='%s' and user_id=(select user_id from users where login_id='%s')" %(did,lid)
	print(q)
	res=select(q)
	if res :
		data['status']  = 'success'
		data['data'] = res
	else:
		data['status']	= 'failed'
	data['method']='userviewfeedback'
	return  demjson.encode(data)



@api.route('/usersendfeedback',methods=['get','post'])
def usersendfeedback():
	data={}
	did = request.args['did']
	feed_des = request.args['feed_des']
	lid = request.args['lid']
	

	q="insert into feedback values(null,(select user_id from users where login_id='%s'),'%s','%s',curdate())" %(lid,did,feed_des)
	id=insert(q)
	
	data['status']  = 'success'
	
	data['method']='usersendfeedback'
	return  demjson.encode(data)



@api.route('/Userviewdesignsrequested',methods=['get','post'])
def Userviewdesignsrequested():
	data={}
	lid=request.args['lid']
	q="SELECT * from requests where  user_id=(select user_id from users where login_id='%s')" %(lid)
	print(q)
	res=select(q)
	if res :
		data['status']  = 'success'
		data['data'] = res
	else:
		data['status']	= 'failed'
	data['method']='Userviewdesignsrequested'
	return  demjson.encode(data)


@api.route('/Userviewbookings',methods=['get','post'])
def Userviewbookings():
	data={}
	lid=request.args['lid']
	q="SELECT * from bookings inner join designs using(design_id) where  user_id=(select user_id from users where login_id='%s') order by booking_id desc" %(lid)
	print(q)
	res=select(q)
	if res :
		data['status']  = 'success'
		data['data'] = res
	else:
		data['status']	= 'failed'
	data['method']='Userviewbookings'
	return  demjson.encode(data)


@api.route('/viewcustom',methods=['get','post'])
def viewcustom():
	data={}
	lid=request.args['lid']
	q="SELECT * from custom_designs where " %(lid)
	print(q)
	res=select(q)
	if res :
		data['status']  = 'success'
		data['data'] = res
	else:
		data['status']	= 'failed'
	data['method']='viewcustom'
	return  demjson.encode(data)