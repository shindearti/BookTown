import httpClient from '../http-common';

const supplierList = () => {
    return httpClient.get('/supplier/list');
};

const removeSupplier = (id) => {
    return httpClient.get(`/admin/removesupplier/${id}`);
};

const addSupplier = (data) => {
    return httpClient.post('/admin/newSupplier', data);
};

const updateSupplier = (data, supplierid) => {
    return httpClient.post(`/admin/updatesupplier/${supplierid}`, data);
};

const getSupplierDetails = (supplierid) => {
    return httpClient.get(`/supplier/supplierdetails/${supplierid}`);
};

const getProductList = () => {
    return httpClient.get(`/supplier/allproducts`);
};

const removeProduct = (id) => {
    return httpClient.get(`/admin/removeproduct/${id}`);
}

const getSpecificProductDetails = (supplierid, productid) => {
    return httpClient.get(`/supplier/products/${supplierid}/${productid}`);
}

export default { supplierList, removeSupplier, addSupplier, updateSupplier, getSupplierDetails, getProductList, removeProduct, getSpecificProductDetails }