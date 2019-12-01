package br.ufal.ic.ufood.presentation.auth.signup

import br.ufal.ic.ufood.data.user.UserRepositoryImpl
import br.ufal.ic.ufood.domain.Credentials
import br.ufal.ic.ufood.domain.User
import br.ufal.ic.ufood.presentation.auth.LABEL_TYPE_EMAIL
import br.ufal.ic.ufood.presentation.auth.LABEL_TYPE_PASSWORD
import br.ufal.ic.ufood.presentation.auth.LABEL_TYPE_PHONE_NUMBER
import br.ufal.ic.ufood.presentation.auth.MSG_WELCOME_USER
import br.ufal.ic.ufood.presentation.shared.mvp.BasicConsole

class SignUpConsole : BasicConsole(), SignUpView {

    private val presenter: SignUpPresenter by lazy { SignUpPresenter(UserRepositoryImpl()) }

    override fun start() {
        super.start()
        presenter.attachView(this)
        showMenu()
    }

    override fun stop() {
        super.stop()
        presenter.detachView()
    }

    override fun requestUser(): User {
        print(LABEL_TYPE_EMAIL)
        val email = scanner.nextLine()
        print(LABEL_TYPE_PASSWORD)
        val password = scanner.nextLine()
        print(LABEL_TYPE_EMAIL)
        val name = scanner.nextLine()
        print(LABEL_TYPE_PHONE_NUMBER)
        val phoneNumber = scanner.nextLine()

        return User(Credentials(email, password), name, phoneNumber)
    }

    override fun onSignUpSuccess(user: User) {
        println(String.format(MSG_WELCOME_USER, user.name))
        holdOutput()
    }

    override fun onSignUpError(message: String) {
        println(message)
        holdOutput()
        showMenu()
    }

    private fun showMenu() {
        println("SIGN UP\n")
        println("1 - Enter user")
        println("0 - Back")
        print("\nOption: ")

        when (scanner.nextLine().toInt()) {
            1 -> presenter.onEnterUser()
            0 -> {
                holdOutput()
                stop()
            }
            else -> println("Invalid option.")
        }
    }

}