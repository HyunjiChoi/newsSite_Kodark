CREATE DEFINER=`root`@`localhost` PROCEDURE `users_procedure`(
   in _switch varchar(20)
    , in _id int
    , inout _email varchar(50)    
    , inout _nickName varchar(20)
    , inout _pwd varchar(300)
    , inout _name varchar(20)    
    , inout _local varchar(50)
    , inout _birth datetime
    , inout _gender char(1)
    , inout _image varchar(200)
    , out _auth char(8)
    , out result_set varchar(10)

)
begin
declare emailCheck int;
declare idCount int;

-- 회원정보 수정(34)--------------------------------------------
   if _switch = 'update_detail' then
      select count(*) into idCount from users where id = _id;        
      if idCount > 0 then
         if _email is null then
            set result_set = '404';
		else    
            update user_detail 
            set nickname = _nickName, name = _name ,local = _local, birth = _birth , gender = _gender ,image = _image  
            where id = _id;            
            set result_set = '204';
		end if;            
        elseif idCount < 0 or idCount = 0 then 
         set result_set = '401';
        else 
         set result_set = '500';
      end if;
      
-- 회원정보(33)--------------------------------------------
    elseif _switch = 'mypage_detail' then
	select count(*) into idCount from users where id = _id;   
	select exists (select email from users where id = _id) into emailCheck;   
		if idCount > 0 then
			if emailCheck = 1 then
				select image, nick_name, name, local, date_format(birth,'%Y-%m-%d') as birth, gender
				from user_detail where user_id = _id;
				set result_set = '204';
			else    
				set result_set = '404';
			end if;    
		elseif idCount < 0 or idCount = 0 then 
			 set result_set = '401';
		else 
			 set result_set = '500';
      end if;

-- 회원 탈퇴(32) --------------------------------------------------
	elseif _switch = 'delete' then
	select count(*) into idCount from users where  id = _id;   
	select count(email) into emailCheck from users where id = _id;
      if idCount > 0 then
         if emailCheck = 0 then
            set result_set = '404';
         else    
            update users
            set status = 'stop', email = null where id = _id;
            set result_set = '204';
            end if;    
      elseif idCount < 0 or idCount = 0 then 
         set result_set = '401';
      else 
         set result_set = '500';    
	end if;
        
-- 비밀번호 수정 -------------------------------------------------
	elseif _switch = 'update_password' then 
	select count(*) into idCount from users where id = _id;   
        
	if idCount > 0 then
		if _email is null then
			set result_set = '404';
		else    
            update users
            set pwd = _pwd;
            set result_set = '200';
		end if;    
	elseif idCount < 0 or idCount = 0 then 
         set result_set = '401';
	else 
         set result_set = '500';    
    end if;

	elseif _switch = 'signin_info' then
    select count(*) into idCount from users where  id = _id;   
        
		if idCount > 0 then           
			set result_set = '200';
		else 
			set result_set = '401';
		  
		end if;
    
	end if;
      
-- 마이페이지(28)-----------------------------------------------------
	if _switch = 'my_page' then
		select count(*) into idCount from users where id = _id;   
		select email into _email from users where id = _id;
		select auth into _auth from users where id = _id; 
		
        if idCount > 0 then
			if _email is null then
			   set result_set = '404';
			else
				set result_set = '200';
			end if;
        end if;
        
	end if;
   
END