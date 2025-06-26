RUST_TARGET_LINUX := x86_64-unknown-linux-gnu
RUST_TARGET_WIN := x86_64-pc-windows-gnu
RESOURCE_DIR := ../thumbnail/src/main/resources/

all: linux windows copy_resources

linux:
	@echo "Building Rust library for Linux..."
	cargo build --release --target $(RUST_TARGET_LINUX)

windows:
	@echo "Building Rust library for Windows..."
	cargo build --release --target $(RUST_TARGET_WIN)

copy_resources:
	@echo "Copying native libraries to resources..."
	@mkdir -p $(RESOURCE_DIR)/native/linux $(RESOURCE_DIR)/native/windows
	
	@cp target/$(RUST_TARGET_LINUX)/release/lib*.so $(RESOURCE_DIR)/native/linux/ || true
	
	@cp target/$(RUST_TARGET_WIN)/release/*.dll $(RESOURCE_DIR)/native/windows/ || true
	
	@echo "Copied files:"
	@ls -l $(RESOURCE_DIR)/*

clean:
	@echo "Cleaning build artifacts..."
	@cargo clean
	@rm -rf $(RESOURCE_DIR)
	@echo "Clean complete"

install_deps:
	@echo "Installing dependencies..."
	sudo apt update
	sudo apt install -y gcc-mingw-w64-x86-64
	curl https://sh.rustup.rs -sSf | sh -s -- -y --default-toolchain stable
	$$HOME/.cargo/bin/rustup target add $(RUST_TARGET_LINUX) $(RUST_TARGET_WIN)

.PHONY: all linux windows copy_resources clean install_deps