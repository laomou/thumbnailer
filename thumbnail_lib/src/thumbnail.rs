use image::{GenericImageView};

pub fn generate_thumbnail_rgba(
    input_path: &str,
    max_width: u32,
    max_height: u32,
) -> Result<(Vec<u8>, u32, u32), Box<dyn std::error::Error>> {
    let img = image::open(input_path)?;

    let (width, height) = calculate_thumbnail_size(img.dimensions(), max_width, max_height);
    
    let thumbnail = img.resize_exact(width, height, image::imageops::FilterType::Lanczos3);
    
    let rgba_img = thumbnail.to_rgba8();

    Ok((rgba_img.into_raw(), width, height))
}

fn calculate_thumbnail_size(
    (orig_width, orig_height): (u32, u32),
    max_width: u32,
    max_height: u32,
) -> (u32, u32) {
    let ratio = orig_width as f32 / orig_height as f32;
    
    let mut width = max_width;
    let mut height = (max_width as f32 / ratio) as u32;
    
    if height > max_height {
        height = max_height;
        width = (max_height as f32 * ratio) as u32;
    }
    
    (width, height)
}