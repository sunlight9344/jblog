--
-- user
--
desc user;
insert user values('sunlight9344','전예준',password('1111'));
select * from user;
delete from user;

select id, name from user where id='sunlight9344' and password=password('1111');

--
-- blog
--
desc blog;
insert blog values('내블로그이름', '/assets/upload-images/202382557382.png', 'sunlight9344');
select * from blog;
select title, image, blog_id as blogId from blog where blog_id='sunlight9344';

update blog set title='', image='' where blog_id = '';

--
-- category
--
desc category;
insert category values(null, 'poscodx 6기', '아재밌다', 'sunlight9344');
select no, name, description, blog_id as blogId from category;
select * from category;
delete from category, post
		where no=2;

--
-- post
--
desc post;
insert post values(null, '내포스트', '포스트내용', '3');

select * from post;

select c.no, c.title, c.contents, c.category_no as categoryNo
from blog a, category b, post c
where a.blog_id = b.blog_id
and  b.no = c.category_no
and a.blog_id = 'sunlight9344'
and c.category_no = 6;

select count(*) from post where category_no = 6;

select no, title, contents, category_no as categoryNo from post where no=1;