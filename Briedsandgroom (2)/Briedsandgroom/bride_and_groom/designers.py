from flask import *
from database import *
import uuid

designers=Blueprint('designers',__name__)

@designers.route('/designer_home')
def designer_home():
	return render_template('designer_home.html')

@designers.route('/designer_add_product_category',methods=['get','post'])
def designer_add_product_category():

	data={}

	q="SELECT * FROM `categories` WHERE `designer_id`='%s'"%(session['did'])
	res=select(q)
	data['cat']=res

	if 'submit' in request.form:
		cname=request.form['catname']
		des=request.form['des']

	
		q="INSERT INTO `categories`(`designer_id`,`category_name`,`description`) VALUES('%s','%s','%s')"%(session['did'],cname,des)
		insert(q)



		flash('success')

		return redirect(url_for('designers.designer_add_product_category'))

	if 'action' in request.args:
		action=request.args['action']
		ids=request.args['id']
	else:
		action=None
		print(action)

	if action=='delete':

		q="DELETE FROM `categories` WHERE category_id='%s'"%(ids)
		delete(q)
		q2="DELETE FROM `designs` WHERE category_id='%s'"%(ids)
		delete(q2)

		return redirect(url_for('designers.designer_add_product_category'))

	return render_template('designer_add_product_category.html',data=data)

@designers.route('/designer_add_design',methods=['get','post'])
def designer_add_design():
	data={}

	q1="SELECT * FROM `designs` INNER JOIN `categories` USING(`category_id`) WHERE designer_id='%s'"%(session['did'])
	res=select(q1)
	data['designs']=res


	q="SELECT * FROM `categories` WHERE `designer_id`='%s'"%(session['did'])
	res=select(q)
	data['categories']=res

	if 'submit' in request.form:
		catid=request.form['cat_id']
		dname=request.form['dname']
		model=request.form['model']
		material=request.form['material']
		details=request.form['details']

		photo=request.files['photo']
		path='static/designes/'+str(uuid.uuid4())+photo.filename
		photo.save(path)

		price=request.form['price']
		made_stock=request.form['stock']
		q="INSERT INTO `designs`(`category_id`,`design_name`,`model`,`material`,`details`,`photo`,`price`,`made_stock`) VALUES('%s','%s','%s','%s','%s','%s','%s','%s')"%(catid,dname,model,material,details,path,price,made_stock)
		insert(q)

		flash('success')

		return redirect(url_for('designers.designer_add_design'))

	if 'action' in request.args:
		action=request.args['action']
		ids=request.args['id']
	else:
		action=None
		print(action)

	if action=='delete':

		q="DELETE FROM `designs` WHERE design_id='%s'"%(ids)
		delete(q)

		return redirect(url_for('designers.designer_add_design'))


	return render_template('designer_add_design_on_product_category.html',data=data)

@designers.route('/designer_view_booking',methods=['get','post'])
def designer_view_booking():

	data={}
	q="SELECT *,`bookings`.`status` AS bstatus FROM `bookings` INNER JOIN users USING(`user_id`) INNER JOIN `designs` USING (`design_id`) INNER JOIN `categories` USING (`category_id`) INNER JOIN `designers` USING (`designer_id`) WHERE designer_id='%s'"%(session['did'])
	print(q)
	res=select(q)
	if res:
		data['bookings']=res
		b_id=res[0]['booking_id']

		if 'action' in request.args:
			action=request.args['action']
			ids=request.args['id']
		else:
			action=None
			print(action)

		if action=='accept':

			q="UPDATE `bookings` SET `date_time`=NOW(), `status`='accepted' WHERE `booking_id`='%s'"%(ids)
			update(q)
			# q1="INSERT INTO `delivery`(`booking_id`)VALUES('%s')"%(ids)
			# insert(q1)

			flash('success')

			return redirect(url_for('designers.designer_view_booking'))

		if action=='set':
			ids=request.args['id']

			q1="INSERT INTO `delivery`(`booking_id`,`book_type`,`date_time`,`status`)VALUES('%s','design',NOW(),'delivered')"%(ids)
			insert(q1)
			q="UPDATE `bookings` SET `status`='delivered' WHERE `booking_id`='%s'"%(ids)
			update(q)
			

			flash('success')

			return redirect(url_for('designers.designer_view_booking'))


		q="SELECT *,status as bs FROM `delivery` WHERE `booking_id`='%s' AND `status`='Delivered'"%(b_id)
		rs=select(q)
		data['rs']=rs

	return render_template('designer_view_booking.html',data=data)

@designers.route('/designer_view_payment',methods=['get','post'])
def designer_view_payment():

	data={}

	ids=request.args['id']

	q="SELECT *  FROM payment INNER JOIN `bookings` USING (`booking_id`) INNER JOIN `designs` USING (design_id) WHERE booking_id='%s'"%(ids)
	res=select(q)
	data['payments']=res

	return render_template('designer_view_payment.html',data=data)

@designers.route('/designers_view_customer_requests',methods=['get','post'])
def designers_view_customer_requests():
	data={}

	q="SELECT * FROM `requests` INNER JOIN `users` USING (`user_id`) WHERE designer_id='%s'"%(session['did'])
	res=select(q)
	data['req']=res

	return render_template('designer_view_customer_requests.html',data=data)

@designers.route('/designer_sent_sample_design',methods=['get','post'])
def designer_sent_sample_design():

	data={}

	ids=request.args['id']

	q="SELECT * FROM `custom_designs`"
	res=select(q)
	data['custom']=res


	if 'submit' in request.form:
		ids=request.args['id']
		details=request.form['details']
		material=request.form['material']
		price=request.form['price']

		image=request.files['image']
		path='static/custom_designs'+str(uuid.uuid4())+image.filename
		image.save(path)

		 

		q="INSERT INTO `custom_designs`(`request_id`,`details`,`material`,`price`,`image`,`date_time`,`status`)VALUES('%s','%s','%s','%s','%s',NOW(),'pending')"%(ids,details,material,price,path)
		insert(q)

		flash('success')

		return redirect(url_for('designers.designer_sent_sample_design'))

	if 'action' in request.args:
		action=request.args['action']
		ids=request.args['id']
		data['ids']=ids
		ids1=request.args['ids']
	else:
		action=None
		print(action)

	if action=='deliver':
		q1="INSERT INTO `delivery`(`booking_id`,`book_type`,`date_time`,`status`)VALUES('%s','custom_design',NOW(),'delivered')"%(ids)
		insert(q1)
		q2="UPDATE `custom_designs` SET `status`='delivered' WHERE `request_id`='%s'"%(ids)
		update(q2)
		# q="UPDATE `delivery` SET `booking_id`='%s',`book_type`='custom design',`date_time`=NOW(),`status`='delivered' where delivery_id='%s'"%(id,ids1)
		# update(q)

		flash('success')

		return redirect(url_for('designers.designer_sent_sample_design',id=data['ids']))

	

	return render_template('designer_sent_sample_design.html',data=data)

@designers.route('/designers_view_rating',methods=['get','post'])
def designers_view_rating():

	data={}
	q="SELECT * FROM `ratings` WHERE `designer_id`='%s'"%(session['did'])
	res=select(q)
	data['rate']=res

	return render_template('designer_view_rating.html',data=data)

@designers.route('/designer_view_feedback',methods=['get','post'])
def designer_view_feedback():

	data={}

	q="SELECT * FROM `feedback` WHERE `designer_id`='%s'"%(session['did'])
	res=select(q)
	data['feedback']=res

	return render_template('designer_view_feedback.html',data=data)