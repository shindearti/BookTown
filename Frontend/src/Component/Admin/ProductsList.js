import { useEffect, useState } from 'react';
import supplierServices from '../../Services/supplier.services';
import { useNavigate } from "react-router-dom";
import AddProduct from './AddProduct'
import adminServices from '../../Services/admin.services';
import AdminNavBar from './AdminNavBar';
import toast, { Toaster } from 'react-hot-toast';

function ProductsList() {
    const [products, setProducts] = useState([])
    const [supplier, setSupplier] = useState([])
    const [categories, SetCategories] = useState([])
    const [supplierId, setSupplierId] = useState('');
    const [modalId, setModalId] = useState('')
    const [newName, setNewName] = useState('')
    const [newAuthor, setNewAuthor] = useState('')
    const [newStock, setNewStock] = useState('')
    const [newPrice, setNewPrice] = useState('')
    const [newCat, setNewCat] = useState('')

    let formdata = new FormData();
    const onFileChange = (e) => {
        console.log(e.target.files[0])
        if (e.target && e.target.files[0]) {
            formdata.append('imgFile', e.target.files[0])
        }
    }

    const handleSubmit = (id) => {

        adminServices.addProductImage(formdata, id)
            .then(response => {
                console.log('Image Uploaded', response.data)
                toast.success('Image Uploaded. Auto-Redirecting....',
                    {
                        style: {
                            borderRadius: '10px',
                            background: '#333',
                            color: '#fff',
                        },
                    }
                )
                setTimeout(() => {
                    window.location.reload();
                }, 2500)
            })
            .catch(error => {
                console.log("Something went wrong", error)
                toast.error('Something went wrong!',
                    {
                        style: {
                            borderRadius: '10px',
                            background: '#333',
                            color: '#fff',
                        },
                    }
                )
            })

    }
    const navigate = useNavigate();
    const init = () => {
        supplierServices.getProductList()
            .then(response => {
                console.log('Printing Products data', response.data);
                setProducts(response.data);
            })
            .catch(error => {
                console.log('Something went wrong', error);
            })

            supplierServices.supplierList()
            .then(response => {
                console.log('Printing supplier data', response.data);
                setSupplier(response.data);
            })
            .catch(error => {
                console.log('Something went wrong', error);
            })
        adminServices.getCategory()
            .then(response => {
                console.log('Printing Category data', response.data);
                SetCategories(response.data);
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
        supplierServices.removeProduct(id)
            .then(response => {
                console.log('book deleted successfully', response.data);
                init();
            })
            .catch(error => {
                console.log('Something went wrong', error);
            })
    }

    const updateData = (id) => {
        if (newName === "" || newAuthor === "" || newStock === "" || newPrice === "" || newCat === "") {
            console.log("Empty")
            toast.error('Something went wrong!',
                {
                    style: {
                        borderRadius: '10px',
                        background: '#333',
                        color: '#fff',
                    },
                }
            )
            return;
        }

        formdata.append('title', newName)
        formdata.append('author', newAuthor)
        formdata.append('unitPrice', newPrice)
        formdata.append('stock', newStock)
        formdata.append('catid', newCat)

        adminServices.updateProduct(id, formdata)
            .then(response => {
                console.log("Updated", response)
                toast.success('Book Updated. Auto-Redirecting....',
                    {
                        style: {
                            borderRadius: '10px',
                            background: '#333',
                            color: '#fff',
                        },
                    }
                )
                setTimeout(() => {
                    window.location.reload();
                }, 2500)

            })
            .catch(error => {
                console.log("Something went wrong", error)
                toast.error('Something went wrong!',
                    {
                        style: {
                            borderRadius: '10px',
                            background: '#333',
                            color: '#fff',
                        },
                    }
                )
            })
    }

    const updateModalData = (id, newName,newAuthor, newStock, newPrice, newCat) => {
        setModalId(id)
        setNewName(newName)
        setNewAuthor(newAuthor)
        setNewCat(newCat)
        setNewPrice(newPrice)
        setNewStock(newStock)
    }

    return (
        <div>
            <AdminNavBar />
            <div className="container">
                <h3 className='mt-2'>List of Books</h3>
                <hr />
                <div>
                    {/* Modal Button */}
                    <a className="btn btn-primary mb-3 " data-bs-toggle="modal" href="#exampleModalToggle" role="button">Add New Book</a>

                    {/* 1st  Modal Component */}
                    <div className="modal fade" id="exampleModalToggle" aria-hidden="true" aria-labelledby="exampleModalToggleLabel" tabIndex="-1">
                        <div className="modal-dialog">
                            <div className="modal-content">
                                <div className="modal-header">
                                    <h5 className="modal-title" id="exampleModalToggleLabel">Choose Supplier</h5>
                                    <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div className="modal-body">
                                    <select className="form-select" aria-label="Default select example" name="id"
                                        onChange={(event) => {
                                            setSupplierId(event.target.value);
                                        }}
                                    >
                                        <option value="" defaultValue>Open This Select Menu</option>
                                        {
                                            supplier.map(f => (
                                                <option key={f.supplierId} value={f.supplierId}>{f.firstname + ' ' + f.lastname}</option>
                                            ))
                                        }
                                    </select>
                                </div>
                                <div className="modal-footer">
                                    <button className="btn btn-primary" data-bs-target="#exampleModalToggle2" data-bs-toggle="modal">Go To Next Step</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    {/* 2st  Modal Component */}
                    <div className="modal fade" id="exampleModalToggle2" aria-hidden="true" aria-labelledby="exampleModalToggleLabel2" tabIndex="-1">
                        <div className="modal-dialog modal-xl">
                            <div className="modal-content">
                                <div className="modal-header">
                                    <h5 className="modal-title" id="exampleModalToggleLabel2">Add New Book</h5>
                                    <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div className="modal-body">
                                    <AddProduct id={supplierId} />
                                </div>
                            </div>
                        </div>
                    </div>

                    <button type="button" className="btn btn-primary mb-3 float-end" onClick={() => { navigate("/admin") }}>Go To Back Page</button>
                    <table className="table table-bordered table-striped">
                        <thead className="thead-dark">
                            <tr>
                                <th>Book Id</th>
                                <th>Book Name</th>
                                <th>Book Author</th>
                                <th>Stock</th>
                                <th>Price per Unit</th>
                                <th>Category</th>
                                <th>Image</th>
                                <th className='text-center'>Functions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                products.map(p => (
                                    <tr key={p.id}>
                                        <td>{(products.indexOf(p) + 1)}</td>
                                        <td>{p.title}</td>
                                        <td>{p.author}</td>
                                        <td>{p.stock}</td>
                                        <td>{p.unitPrice}</td>
                                        <td>{p.category.categoryName}</td>
                                        <td>
                                            <figure>
                                                <img src={`http://localhost:8080/BookTown/admin/${p.id}`} alt='productImage' width={75} />
                                                <figcaption> {p.imagePath} </figcaption>
                                            </figure>
                                        </td>
                                        <td className='text-center'>


                                            {/* Modal Trigger for product update */}
                                            <button type="button" className="btn btn-info mx-1" data-bs-toggle="modal"
                                                data-bs-target="#exampleModal1" onClick={() => updateModalData(p.id, p.title, p.author,p.stock, p.unitPrice, p.category.categoryId)}>Update
                                            </button>

                                            {/* Modal Component for product update */}
                                            <div className="modal fade" id="exampleModal1" tabIndex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                                <div className="modal-dialog">
                                                    <div className="modal-content">
                                                        <div className="modal-header">
                                                            <h5 className="modal-title" id="exampleModalLabel">Update Book</h5>
                                                            <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                        </div>
                                                        <div className="modal-body">



                                                            <div className="row mb-3">
                                                                <div className="col">
                                                                    <input type="text" className="form-control" placeholder="New Book name" aria-label="New Product Name"
                                                                        value={newName}
                                                                        onChange={(e) => setNewName(e.target.value)}
                                                                    />
                                                                </div>
                                                                <div className="col">
                                                                    <input type="text" className="form-control" placeholder="New Author" aria-label="New Author"
                                                                        value={newAuthor}
                                                                        onChange={(e) => setNewAuthor(e.target.value)}
                                                                    />
                                                                </div>
                                                                <div className="col">
                                                                    <input type="number" className="form-control" placeholder="New product stock" aria-label="New Product stock"
                                                                        value={newStock}
                                                                        onChange={(e) => setNewStock(e.target.value)}
                                                                    />
                                                                </div>
                                                            </div>
                                                            <div className="row">
                                                                <div className="col">
                                                                    <input type="text" className="form-control" placeholder="New Price" aria-label="New Price"
                                                                        value={newPrice}
                                                                        onChange={(e) => setNewPrice(e.target.value)}
                                                                    />
                                                                </div>
                                                                <div className="col">

                                                                    <select className="form-select" aria-label="Default select example" name="categoryData" value={newCat}
                                                                        onChange={(event) => {
                                                                            setNewCat(event.target.value);
                                                                        }}
                                                                    >
                                                                        {
                                                                            categories.map(c => (
                                                                                <option key={c.categoryId} value={c.categoryId}>{c.categoryName}</option>
                                                                            ))
                                                                        }
                                                                    </select>


                                                                </div>
                                                            </div>

                                                        </div>
                                                        <div className="modal-footer">
                                                            <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                            <button type="button" className="btn btn-primary" onClick={() => updateData(modalId)}>Save Data</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            {/* Modal Trigger for add image */}
                                            <button type="button" className="btn btn-success mx-3" data-bs-toggle="modal"
                                                data-bs-target="#exampleModal" onClick={() => setModalId(p.id)}>Add Image
                                            </button>

                                            {/* Modal Component  for add image */}
                                            <div className="modal fade" id="exampleModal" tabIndex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                                <div className="modal-dialog">
                                                    <div className="modal-content">
                                                        <div className="modal-header">
                                                            <h5 className="modal-title" id="exampleModalLabel">Add Image</h5>
                                                            <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                        </div>
                                                        <div className="modal-body">

                                                            <form>
                                                                <div className="mb-3">
                                                                    <input className="form-control" type="file" id="formFile" onChange={onFileChange} />
                                                                </div>
                                                            </form>

                                                        </div>
                                                        <div className="modal-footer">
                                                            <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                            <button type="button" className="btn btn-primary" onClick={() => handleSubmit(modalId)}>Upload Image</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <button className="btn btn-danger ml-2" onClick={() => {
                                                handleDelete(p.id);
                                            }}>Delete</button>
                                        </td>
                                    </tr>
                                ))
                            }
                        </tbody>
                    </table>

                </div>
                <div className="text-center text-muted" style={{ marginTop: '100px', marginBottom: '35px' }}>
                    Copyright &copy; 2023 &mdash; BookTown
                </div>
            </div>
        </div>
    );
}

export default ProductsList;