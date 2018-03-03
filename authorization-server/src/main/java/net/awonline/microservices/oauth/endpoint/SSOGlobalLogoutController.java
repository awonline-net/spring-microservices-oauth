package net.awonline.microservices.oauth.endpoint;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SSOGlobalLogoutController {

	@Autowired
	FindByIndexNameSessionRepository<? extends Session> sessions;

	@RequestMapping(method = RequestMethod.POST, value = "/globalLogout")
	@ResponseBody
	public void logout(Principal principal) {

		if (principal != null && principal.getName() != null) {

			Map<String, ? extends Session> userSessions = this.sessions.findByIndexNameAndIndexValue(
					FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, principal.getName());

			for (String sessionId : userSessions.keySet()) {
				this.sessions.delete(sessionId);
			}
		}
	}
}
