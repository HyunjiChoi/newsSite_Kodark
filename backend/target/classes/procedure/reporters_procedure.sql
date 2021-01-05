CREATE DEFINER=`root`@`localhost` PROCEDURE `reporter_procedure`(
	in _id int
)
BEGIN
    
    select
    users.id,
    users.email,
    ud.nick_Name,
    ud.name,
    ud.local,
    ud.birth,
    ud.gender,
    ud.image,
	(select count(*) from subscriber where reporter_id = users.id) as subNum
    from 
    user

    inner join user_detail ud
    on users.id = ud.user_id
    where auth ='reporter' AND users.id = _id
    group by users.id
    ;
    
END