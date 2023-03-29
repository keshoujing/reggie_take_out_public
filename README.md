# Reggie Takeout

---

# Project Demo

- Backend: [https://bit.ly/reggie_backend](https://bit.ly/reggie_backend)
- Frontend: [https://bit.ly/reggie_front](https://bit.ly/reggie_front) (Please use PHONE MODE to visit this site)
    - Phone mode: Go to frontend website → Right click mouse → Click "inspect" → locate and click on the "Toggle device toolbar" button located at the top left corner of the window. This button typically looks like a small phone or tablet icon.

## Instruction:

- There is default username and password in backend page, just click log in, that is.
- In order to log into frontend page (2 ways):
    1. You can login to backend first, using default username and password, then click or refresh frontend page.
    2. You can enter your email address and wait for verification code send to your email address.

### Please note that this project is a demo and the actions performed within it are for testing purposes only. Listing items or performing any other actions within the demo will not cause any harm to your system or data.

---

# 1. Project Introduction

- The project offers tailored software solutions for catering companies, consisting of two distinct components: a comprehensive backend management system and a user-friendly mobile application.
- The backend management system is designed to be used by the restaurant's internal staff, enabling them to easily manage and maintain categories, dishes, meals, orders, and employee information.
- The mobile application is primarily intended for consumer use, providing an online platform for browsing dishes, creating shopping carts, and placing orders.

---

- Backend: http://localhost/backend/index.html
- Frontend: http://localhost/front/index.html

---

![2](https://user-images.githubusercontent.com/80348813/228588159-cb00f1a7-d64d-4313-b0ef-1291eaf6b552.png)

---

# 2. Backend

![1](https://user-images.githubusercontent.com/80348813/228588156-5a7465e3-367c-4b10-8e75-b2a8c24fd86f.png)

---

Main Features:

| Module | Description |
| --- | --- |
| Login/Logout | Employees must log in to access the system management backend |
| Employee Management | Administrators can manage employee information in the system, including functions such as search, add, edit, disable, etc. |
| Category Management | Mainly used to manage and maintain the menu or meal categories, including functions such as search, add, modify, delete, etc. |
| Dish Management | Mainly used to maintain the information of dishes under each category, including functions such as search, add, modify, delete, start sale, stop sale, etc. |
| Meal Management | Mainly used to maintain the meal information, including functions such as search, add, modify, delete, start sale, stop sale, etc. |
| Order Details | Mainly used to maintain the order information placed by users on the mobile end, including functions such as search, cancel, dispatch, complete, and order report downloading. |

---

# 3. Frontend

![3](https://user-images.githubusercontent.com/80348813/228588165-e56f4b14-7d3c-42f1-af18-18c5687fddb0.png)

---

Main Features:

| Module | Description |
| --- | --- |
| Login/Logout | On the mobile end, users need to log in to the app to place orders. |
| Ordering - Menu | The ordering interface needs to display menu/meal categories, and load corresponding dish information based on the selected category for users to query and select. |
| Ordering - Shopping Cart | The dishes selected by the user will be added to their shopping cart, which mainly includes functions such as querying, adding, deleting, and clearing the shopping cart. |
| Order Payment | After selecting the dishes/set meals, users can proceed to checkout and pay for the items in their shopping cart. |
| Personal Information | The user's basic information will be displayed on the personal center page, where the user can manage delivery addresses and query historical order data. |

---

# 4. Selected Technologies

| User Layer | H5, Vue.js, ElementUI |
| --- | --- |
| Gateway Layer | Nginx |
| Application Layer | SpringBoot, Spring, SpringMVC, SpringSession, lombok, Swagger |
| Data Layer | MySQL, Mybatis Plus, Redis |
| Tools | Git, Maven, Junit |
