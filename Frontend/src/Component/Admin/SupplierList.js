import { useEffect, useState } from 'react';
import supplierServices from '../../Services/supplier.services';
import { useNavigate} from "react-router-dom";
import AdminNavBar from './AdminNavBar';

function SupplierList() {

    const [supplier, setSupplier] = useState([]);
    const navigate = useNavigate();

    const init = () => {
        supplierServices.supplierList()
            .then(response => {
                console.log('Printing Supplier data', response.data);
                setSupplier(response.data);
            })
            .catch(error => {
                console.log('Something went wrong', error);
            })
    }

    useEffect(() => {
        init();
    }, []);

    const handleDelete = (id) => {
        console.log('Printing id', id);
        supplierServices.removeSupplier(id)
            .then(response => {
                console.log('Supplier deleted successfully', response.data);
                init();
            })
            .catch(error => {
                console.log('Something went wrong', error);
            })
    }

    return (
        <div>
            <AdminNavBar />
            <div className="container">
                <h3 className='mt-2'>List of Suppliers</h3>
                <hr />
                <div>

                    <button type="button" className="btn btn-primary mb-3" onClick={() => navigate('/admin/addnewsupplier')}>Add New Supplier</button>
                    <button type="button" className="btn btn-primary mb-3 float-end" onClick={() => { navigate("/admin") }}>Go To Back Page</button>
                    <table className="table table-bordered table-striped text-center">
                        <thead className="thead-dark">
                            <tr>
                                <th>Supplier Id</th>
                                <th>First Name</th>
                                <th>Last Name</th>
                                <th>Phone No.</th>
                                <th>Email</th>
                                <th>Address</th>
                                <th className='text-center'>Functions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                supplier.map(f => (
                                    <tr key={f.supplierId}>
                                        <td>{supplier.indexOf(f) + 1}</td>
                                        <td>{f.firstname}</td>
                                        <td>{f.lastname}</td>
                                        <td>{f.phoneNo}</td>
                                        <td>{f.email}</td>
                                        <td>{f.address}</td>
                                        <td className='text-center'>
                                            <button type="button" className="btn btn-info mx-1" onClick={() =>
                                                navigate(`/admin/updatesupplier/${f.supplierId}`)}>Update</button>

                                            <button type="button" className="btn btn btn-success mx-3" onClick={() =>
                                                navigate(`/admin/addproduct/${f.supplierId}`)}>Add Product</button>

                                            <button className="btn btn-danger ml-2" onClick={() => {
                                                handleDelete(f.supplierId);
                                            }}>Delete</button>
                                        </td>
                                    </tr>
                                ))
                            }
                        </tbody>
                    </table>

                </div>
            </div>
            <div className="text-center  text-muted" style={{marginTop: '100px', marginBottom: '35px'}}>
                Copyright &copy; 2022 &mdash; BookTown
            </div>
        </div>
    );
}

export default SupplierList;