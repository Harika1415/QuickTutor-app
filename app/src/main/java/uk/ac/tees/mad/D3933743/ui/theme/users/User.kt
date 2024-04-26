package uk.ac.tees.mad.D3933743.ui.theme.users

data class User(
    var course:List<Course> = emptyList(),
    var bookmarks:List<String> = emptyList()
)

data class Course(
    val tutorId: String? = null,
    val tutorImage:String? = null,
    val tutorName:String? = null,
    val dateTime : String? = null
)
