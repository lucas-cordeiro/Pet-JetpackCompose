package com.example.androiddevchallenge.data.repository
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.domain.model.*
import com.example.androiddevchallenge.domain.repository.PetRepository
import kotlinx.coroutines.flow.*
import java.util.*

class PetRepositoryImpl : PetRepository {

    private val pets = MutableStateFlow(listOf(
        Pet(
            id = UUID.randomUUID().toString(),
            name = "Kratos",
            description = "This 3 year old sweet boy is Kratos! He is calm, kind and loving, with a playful side! Kratos is well-mannered so he may do well with other dogs or kids but a meet and greet is required. We are unsure if he is good around cats but he can be cat tested upon request. Call us soon to meet this love bug before he's snatched up by a lucky family!",
            category = Category.DOG,
            gender = Gender.M,
            birthDate = 1514841682,
            profilePic = R.drawable.kratos,
            location = Address(
                street = "1518  Rosemont Avenue",
                city = "Longwood",
                state = "Florida"
            ),
            user = User(
                profilePic = R.drawable.alan,
                name = "Alan Haytan",
                location = Address(
                    street = "1518  Rosemont Avenue",
                    city = "Longwood",
                    state = "Florida"
                )
            )
        ),
        Pet(
            id = UUID.randomUUID().toString(),
            name = "Nicolas",
            description = "This is Nicholas, a 5 year old who needs a home. He can be spunky, sweet, happy, loving, laid back and confident. He would prefer to be the only pet so he can get all the attention.",
            category = Category.DOG,
            gender = Gender.M,
            birthDate = 1614453682,
            profilePic = R.drawable.nicolas,
            location = Address(
                street = "182  Broad Street",
                city = "Birmingham",
                state = "Alabama",
            ),
            user = User(
                profilePic = R.drawable.alan,
                name = "Alan Haytan",
                location = Address(
                    street = "182  Broad Street",
                    city = "Birmingham",
                    state = "Alabama",
                )
            )
        ),
        Pet(
            id = UUID.randomUUID().toString(),
            name = "Jenny",
            description = "Meet Jenny she is a 1 year old terrier mix. This girl is nothing but playful and full of kisses. As she is still young she still has a lot of puppy energy. We are still working on walking on a leash but does her best in a harness.",
            category = Category.DOG,
            gender = Gender.F,
            birthDate = 1607631568,
            profilePic = R.drawable.jenny,
            location = Address(
                street = "2604  Davis Avenue",
                city = "San Rafael",
                state = "California",
            ),
            user = User(
                profilePic = R.drawable.alan,
                name = "Alan Haytan",
                location = Address(
                    street = "2604  Davis Avenue",
                    city = "San Rafael",
                    state = "California",
                )
            )
        ),
        Pet(
            id = UUID.randomUUID().toString(),
            name = "Oprah",
            description = "Needs a quiet home and patience for her to relax and feel safe. A sweet girl!",
            category = Category.CAT,
            gender = Gender.F,
            birthDate = 1602361168,
            profilePic = R.drawable.oprah,
            location = Address(
                street = "2024  Hershell Hollow Road",
                city = "Everett",
                state = "Washington"
            ),
            user = User(
                profilePic = R.drawable.alan,
                name = "Alan Haytan",
                location = Address(
                    street = "2024  Hershell Hollow Road",
                    city = "Everett",
                    state = "Washington"
                )
            )
        ),
        Pet(
            id = UUID.randomUUID().toString(),
            name = "Shuri",
            description = "Rescued from a parking garage, this fluffy tailed girl is ready for pets in a real home! ",
            category = Category.CAT,
            gender = Gender.F,
            birthDate = 1594412368,
            profilePic = R.drawable.shuri,
            location = Address(
                street = "4489  Lynn Street",
                city = "Hominy",
                state = "Oklahoma"
            ),
            user = User(
                profilePic = R.drawable.alan,
                name = "Alan Haytan",
                location =  Address(
                    street = "4489  Lynn Street",
                    city = "Hominy",
                    state = "Oklahoma"
                )
            )
        ),
        Pet(
            id = UUID.randomUUID().toString(),
            name = "Cacau",
            description = "The little king of your house",
            category = Category.CAT,
            gender = Gender.M,
            birthDate = 1546377682,
            profilePic = R.drawable.cacau,
            location = Address(
                street = "4514  Tenmile Road",
                city = "Cambridge",
                state = "Massachusetts"
            ),
            user = User(
                profilePic = R.drawable.alan,
                name = "Alan Haytan",
                location = Address(
                    street = "4514  Tenmile Road",
                    city = "Cambridge",
                    state = "Massachusetts"
                )
            )
        ),
        Pet(
            id = UUID.randomUUID().toString(),
            name = "Joplin",
            description = "Joplin loves cashews, sunflower seeds, and playing with toys",
            category = Category.BIRD,
            gender = Gender.M,
            birthDate = 1609539682,
            profilePic = R.drawable.joplin,
            location = Address(
                street = "3240  Archwood Avenue",
                city = "Laramie",
                state = "Wyoming"
            ),
            user = User(
                profilePic = R.drawable.alan,
                name = "Alan Haytan",
                location = Address(
                    street = "3240  Archwood Avenue",
                    city = "Laramie",
                    state = "Wyoming"
                )
            )
        ),
        Pet(
            id = UUID.randomUUID().toString(),
            name = "Baby",
            description = "TWe're like Baby, Baby, Baby, ohhhhh! Or whatever it was that Justin Bieber said. Regardless, we know Baby could be the one to make YOUR heart sing.",
            category = Category.BIRD,
            gender = Gender.M,
            birthDate = 1583871568,
            profilePic = R.drawable.baby,
            location = Address(
                street = "588  Villa Drive",
                city = "Goshen",
                state = "Indiana"
            ),
            user = User(
                profilePic = R.drawable.alan,
                name = "Alan Haytan",
                location = Address(
                    street = "588  Villa Drive",
                    city = "Goshen",
                    state = "Indiana"
                )
            )
        ),
        Pet(
            id = UUID.randomUUID().toString(),
            name = "Goose",
            description = "Goose is a sweet bunny who is gentle and does well with kids. She is very good about not chewing objects around her except for the occasional paper towel roll/cardboard you give her, or sometimes papers.",
            category = Category.OTHER,
            gender = Gender.F,
            birthDate = 1514841682,
            profilePic = R.drawable.goose,
            location = Address(
                street = "2418  Pinnickinick Street",
                city = "Alger",
                state = "Washington"
            ),
            user = User(
                profilePic = R.drawable.alan,
                name = "Alan Haytan",
                location = Address(
                    street = "2418  Pinnickinick Street",
                    city = "Alger",
                    state = "Washington"
                )
            )
        ),
    ))

    override suspend fun getPets(): Flow<List<Pet>> {
        return pets
    }

    override suspend fun getPetById(id: String): Flow<Pet?> {
        return pets.map { it.firstOrNull { it.id == id } }
    }
}