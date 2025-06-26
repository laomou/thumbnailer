use jni::{
    objects::{JClass, JString, JObject, JValue},
    sys::{jobject, jint},
    JNIEnv,
};


mod thumbnail;
use thumbnail::generate_thumbnail_rgba;

#[unsafe(no_mangle)]
pub extern "system" fn Java_com_laomou_thumbnailator_generateThumbnail(
    mut env: JNIEnv,
    _class: JClass,
    input_path: JString,
    max_width: jint,
    max_height: jint,
) -> jobject {

    let input_path : String = env.get_string(&input_path).unwrap().into();

    let max_width = max_width as u32;
    let max_height = max_height as u32;

    let (rgba_data, width, height) = generate_thumbnail_rgba(&input_path, max_width, max_height).unwrap();

    let byte_array = env.byte_array_from_slice(&rgba_data).unwrap();

    let string_class = env.find_class("com/laomou/thumbnailator/ImageThumbnail").unwrap();

    let ctor_method_id = env.get_method_id(&string_class, "<init>", "([BII)V").unwrap();

    let val: JObject = unsafe {
        env.new_object_unchecked(
            &string_class,
            ctor_method_id,
            &[JValue::from(&byte_array).as_jni(), JValue::Int(width as i32).as_jni(), JValue::Int(height as i32).as_jni()],
        )
    }.expect("JNIEnv#new_object_unchecked should return JValue");

    return *val;
}