package fit.iuh;

    class OrderContext {
        private OrderState state;

        public OrderContext() {
            this.state = new NewState(); // Mặc định là mới tạo
        }

        public void setState(OrderState state) {
            this.state = state;
        }

        public void apply() {
            state.handleRequest(this);
        }
}
