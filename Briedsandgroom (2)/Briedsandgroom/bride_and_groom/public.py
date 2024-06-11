from flask import *
from database import *

public=Blueprint('public',__name__)

@public.route('/')
def main_home():
	return render_template('main_home.html')

@public.route('/login',methods=['get','post'])
def login():

	if 'submit' in request.form:
		username=request.form['uname']
		password=request.form['password']

		q="SELECT * FROM login WHERE username='%s' AND `password`='%s'"%(username,password)
		res=select(q)

		if res:
			login_id=res[0]['login_id']

			if res[0]['usertype']=='admin':

				flash('logged in')
				return redirect(url_for('admin.admin_home'))

			if res[0]['usertype']=='designer':
				q="SELECT * FROM `designers` WHERE `login_id`='%s'"%(login_id)
				res1=select(q)
				if res1:
					session['did']=res1[0]['designer_id']
					print(session['did'])

				flash('logged in')
				return redirect(url_for('designers.designer_home'))

			if res[0]['usertype']=='user':

				q="SELECT * FROM 'users' WHERE `login_id`='%s'"%(login_id)
				res1=select(q)

				if res1:
					session['uid']=res1[0]['user_id']

				flash('logged in')

				return redirect(url_for('user.user_home'))



	return render_template('login.html')

@public.route('/designer_registration',methods=['get','post'])
def registration():

	if 'submit' in request.form:

		username=request.form['uname']
		password=request.form['pword']
		designername=request.form['dname']
		place=request.form['place']
		landmark=request.form['landmark']
		pincode=request.form['pin']
		phone=request.form['phone']
		email=request.form['email']

		q="INSERT INTO `login`(`username`,`password`,`usertype`) VALUES('%s','%s','requested')"%(username,password)
		id=insert(q)
		q1="INSERT INTO `designers`(`login_id`,`designer_name`,`place`,`landmark`,`pincode`,`phone`,`email`,`status`) VALUES('%s','%s','%s','%s','%s','%s','%s','requested')"%(id,designername,place,landmark,pincode,phone,email)
		insert(q1)

		flash('success')

		return redirect(url_for('public.login'))

	return render_template('designer_registration.html')