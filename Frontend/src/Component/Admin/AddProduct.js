import { useEffect, useState } from 'react';
import adminServices from '../../Services/admin.services';
import { useParams, useNavigate } from "react-router-dom";
import toast, { Toaster } from 'react-hot-toast';
import AdminNavBar from './AdminNavBar';

function AddProduct(props) {
    const [title, setTitle] = useState('');
    const [author, setAuthor] = useState('');
    const [stock, setStock] = useState('');
    const [unitPrice, setUnitPrice] = useState('');
    const [category, setCategory] = useState('');
    const { supplierId } = useParams();
    const navigate = useNavigate();
    const [categories, SetCategories] = useState([])

    useEffect(() => {
        init();
    }, []);

    const init = () => {
        adminServices.getCategory()
            .then(response => {
                console.log('Printing Category data', response.data);
                SetCategories(response.data);
            })
            .catch(error => {
                console.log('Something went wrong', error);
            })
    }

    var id;
    const addProduct = (e) => {
        e.preventDefault();
        const product = { title, author, stock, unitPrice, category }
        console.log(product)
        console.log('Another Component Value: ', props.id)

        if (supplierId) {
            id = supplierId
        } else {
            id = props.id
        }
        adminServices.addProduct(product, id)
            .then(response => {
                console.log('Book added', response.data)
                toast.success('Book Added. Auto-Redirecting....',
                    {
                        style: {
                            borderRadius: '10px',
                            background: '#333',
                            color: '#fff',
                        },
                    }
                )
                setTimeout(() => {
                    if (supplierId) {
                        navigate('/admin/productslist')
                    } else {
                        window.location.reload();
                    }
                }, 2500)
            })
            .catch(error => {
                console.log('Something went wrong', error)
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
    return (
        <div>
            { supplierId ? <AdminNavBar /> : "" }
            <div className="container h-100">
                <div className="row justify-content-sm-center h-100">
                    <div className="col-xxl-6 col-xl-5 col-lg-5 col-md-7 col-sm-9">
                        <div className="text-center my-4">
                            <img src="https://yt3.ggpht.com/a/AATXAJzW_hSMk1pFOfusLMZWv7poFmYLTNRhUBDnmw=s900-c-k-c0xffffffff-no-rj-mo" alt="logo" width="150" style={{ borderRadius: '50px' }} />
                        </div>
                        <div className="card shadow-lg">
                            <div className="card-body px-5 pt-5">
                                <h1 className="fs-4 card-title fw-bold mb-4">Add New Book</h1>
                                <form onSubmit={(e) => addProduct(e)}>

                                    <div className="row g-3">
                                        <div className="col mb-3">
                                            <label className="mb-2 text-muted" htmlFor="title">Book Name</label>
                                            <input id="title" type="text" className="form-control" name="title" required autoFocus
                                                value={title}
                                                onChange={(e) => setTitle(e.target.value)}
                                            />

                                        </div>

                                        <div className="col mb-3">
                                            <label className="mb-2 text-muted" htmlFor="author">Book Author </label>
                                            <input id="author" type="text" className="form-control" name="author" required autoFocus
                                                value={author}
                                                onChange={(e) => setAuthor(e.target.value)}
                                            />

                                        </div>



                                        <div className="col mb-3">
                                            <label className="mb-2 text-muted" htmlFor="stock">Stock</label>
                                            <input id="stock" type="number" className="form-control" name="stock" required
                                                value={stock}
                                                onChange={(e) => setStock(e.target.value)}
                                            />
                                        </div>
                                    </div>

                                    <div className="row g-3">
                                        <div className="col mb-3">
                                            <label className="mb-2 text-muted" htmlFor="unitPrice">Unit Price</label>
                                            <input id="unitPrice" type="number" className="form-control" name="unitPrice" required
                                                value={unitPrice}
                                                onChange={(e) => setUnitPrice(e.target.value)}
                                            />
                                        </div>

                                        <div className="col mb-3">

                                            <label className="mb-2 text-muted" htmlFor="categoryData">Select Category</label>
                                            <select className="form-select" aria-label="Default select example" name="categoryData"
                                                onChange={(event) => {
                                                    setCategory(event.target.value);
                                                }}
                                            >
                                                <option value="" defaultValue>Open Menu</option>
                                                {
                                                    categories.map(c => (
                                                        <option key={c.categoryId} value={c.categoryId}>{c.categoryName}</option>
                                                    ))
                                                }
                                            </select>

                                        </div>

                                    </div>
                                    <div className="align-items-center d-flex">
                                        <button type="submit" className="btn btn-primary">
                                            Add New Book
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <div className="text-center mt-4 text-muted">
                            Copyright &copy; 2023 &mdash; BookTown
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default AddProduct;