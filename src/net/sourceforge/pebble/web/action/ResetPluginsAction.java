/*
 * Copyright (c) 2003-2006, Simon Brown
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in
 *     the documentation and/or other materials provided with the
 *     distribution.
 *
 *   - Neither the name of Pebble nor the names of its contributors may
 *     be used to endorse or promote products derived from this software
 *     without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package net.sourceforge.pebble.web.action;

import net.sourceforge.pebble.Constants;
import net.sourceforge.pebble.domain.Blog;
import net.sourceforge.pebble.domain.BlogServiceException;
import net.sourceforge.pebble.web.view.ForwardView;
import net.sourceforge.pebble.web.view.View;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Resets the plugins associated with a blog.
 *
 * @author    Simon Brown
 */
public class ResetPluginsAction extends SecureAction {

  /**
   * Peforms the processing associated with this action.
   *
   * @param request  the HttpServletRequest instance
   * @param response the HttpServletResponse instance
   * @return the name of the next view
   */
  public View process(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    Blog blog = (Blog)getModel().get(Constants.BLOG_KEY);

    try {
      blog.removeProperty(Blog.PERMALINK_PROVIDER_KEY);
      blog.removeProperty(Blog.CONTENT_DECORATORS_KEY);
      blog.removeProperty(Blog.BLOG_LISTENERS_KEY);
      blog.removeProperty(Blog.BLOG_ENTRY_LISTENERS_KEY);
      blog.removeProperty(Blog.COMMENT_LISTENERS_KEY);
      blog.removeProperty(Blog.COMMENT_CONFIRMATION_STRATEGY_KEY);
      blog.removeProperty(Blog.TRACKBACK_LISTENERS_KEY);
      blog.removeProperty(Blog.LUCENE_ANALYZER_KEY);
      blog.removeProperty(Blog.LOGGER_KEY);
      blog.storeProperties();
    } catch (BlogServiceException e) {
      e.printStackTrace();
    }

    return new ForwardView("/reloadBlog.secureaction");
  }

  /**
   * Gets a list of all roles that are allowed to access this action.
   *
   * @return  an array of Strings representing role names
   * @param request
   */
  public String[] getRoles(HttpServletRequest request) {
    return new String[]{Constants.BLOG_OWNER_ROLE, Constants.PEBBLE_ADMIN_ROLE};
  }

}