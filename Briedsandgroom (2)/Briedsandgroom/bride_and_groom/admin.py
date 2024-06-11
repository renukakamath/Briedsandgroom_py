from flask import *
from database import *

admin=Blueprint('admin',__name__)

@admin.route('/admin_home')
def admin_home():
	return render_template('admin_home.html')

@admin.route('/admin_view_registered_designers',methods=['get','post'])
def admin_view_registered_designers():

	data={}

	q="SELECT * FROM `designers` INNER JOIN `login` USING(`login_id`)"
	res=select(q)
	data['designers']=res

	if 'action' in request.args:
		action=request.args['action']
	else:
		action=None
		
	print(action)

	if action=='accept':

		ids=request.args['id']
		q="UPDATE `login` SET `usertype`='designer' WHERE login_id='%s'"%(ids)
		id=update(q)
		q2="UPDATE `designers` SET `status`='approved' WHERE login_id='%s'"%(ids)
		update(q2)

		flash('success')

		return redirect(url_for('admin.admin_view_registered_designers'))

	if action=='reject':

		ids=request.args['id']

		q="UPDATE `login` SET `usertype`='rejected' WHERE login_id='%s'"%(ids)
		delete(q)
		q1="UPDATE `designers` SET `status`='rejected' WHERE login_id='%s'"%(ids)
		update(q1)

		flash('success')

		return redirect(url_for('admin.admin_view_registered_designers'))


	return render_template('admin_view_registered_designers.html',data=data)



@admin.route('/admin_view_design_categories',methods=['get','post'])
def admin_view_design_categories():

	data={}

	q="SELECT * FROM `designers` INNER JOIN `categories` USING(`designer_id`)"
	res=select(q)
	data['categories']=res

	return render_template('admin_view_design_categories.html',data=data)

@admin.route('/admin_view_design',methods=['get','post'])
def admin_view_design():

	data={}

	catid=request.args['id']

	q="SELECT * FROM designs WHERE category_id='%s'"%(catid)
	res=select(q)
	data['designs']=res

	return render_template('admin_view_design.html',data=data)

@admin.route('/admin_view_registered_users',methods=['get','post'])
def admin_view_registered_users():

	data={}

	q="SELECT * FROM `users`"
	res=select(q)
	data['users']=res

	return render_template('admin_view_registered_users.html',data=data)

@admin.route('/admin_view_complaints_and_sent_reply',methods=['get','post'])
def admin_view_complaints_and_sent_reply():

	data={}

	q="SELECT * FROM `complaints` INNER JOIN `users` USING(user_id)"
	res=select(q)
	data['complaints']=res


	i=1 
	for row in res:
		if 'replys'+str(i) in request.form:
			reply=request.form['reply'+str(i)]
			id=request.form['ids'+str(i)]
			q="UPDATE `complaints` SET `reply`='%s',`date_time`=CURDATE() WHERE complaint_id='%s'"%(reply,id)
			update(q)

			flash('success')

		

			return redirect(url_for("admin.admin_view_complaints_and_sent_reply"))

		i=i+1




	return render_template('admin_view_complaints_and_sent_reply.html',data=data)


@admin.route('/admin_view_top_rated_designers',methods=['get','post'])
def admin_view_top_rated_designers():

	data={}
	q="SELECT * FROM `ratings` INNER JOIN `designers` USING(`designer_id`) ORDER BY (`rate`) DESC"
	res=select(q)
	data['ratings']=res

	return render_template('admin_view_top_rated_designers.html',data=data)

@admin.route('/admin_view_reported_designers',methods=['get','post'])
def admin_view_reported_designers():

	data={}

	q="SELECT * FROM `designers` INNER JOIN `reported` USING(`designer_id`)"
	res=select(q)
	data['reported']=res

	if 'action' in request.args:
		action=request.args['action']
	else:
		action=None
		print(action)

	if action=='block':
		ids=request.args['id']
		idd=request.args['idd']

		q="UPDATE `login` SET `usertype`='blocked' WHERE login_id='%s'"%(idd)
		update(q)
		q1="UPDATE `designers` SET `status`='blocked' WHERE designer_id='%s'"%(ids)
		update(q1)

		flash('success')
		

		return redirect(url_for('admin.admin_view_reported_designers'))
		


	return render_template('admin_view_reported_designers.html',data=data)