package com.chairbender.consensus.webservice.bean.response;

import com.chairbender.consensus.webservice.entity.Paper;
import com.chairbender.consensus.webservice.entity.Review;
import com.chairbender.consensus.webservice.entity.User;
import com.chairbender.consensus.webservice.repository.ReviewRepository;

import java.util.List;

/**
 * Represents a single paper but also provides
 * information on the current user's review of that
 * paper.
 *
 * review will be null if no review has been submitted.
 */
public class PaperWithUserReview  {
    private Review mReview;
    private Paper mPaper;

    /**
     *
     * @param pReview review for the current user for pPaper. Null if none has been submitted.
     * @param pPaper paper whose details this should encapsulate
     */
    private PaperWithUserReview(Review pReview, Paper pPaper) {
        mReview = pReview;
        mPaper = pPaper;
    }

    /**
     *
     * @param pPaper paper whose details and review for the current user should be retrieved
     * @param pCurrentUser user whose review should be retrieved
     * @param pInRepository repository in which reviews should be searched
     * @return a paper with user review with pPaper and the review for the current user (if any). Review
     *      will be null if none submitted by the current user.
     */
    public static PaperWithUserReview getFromPaperForCurrentUser(Paper pPaper, User pCurrentUser, ReviewRepository pInRepository) {
        List<Review> currentUserReviewList = pInRepository.findByUserIdAndPaperId(pPaper.getId(), pCurrentUser.getId());
        Review currentUserReview = null;
        if (!currentUserReviewList.isEmpty()) {
            currentUserReview = currentUserReviewList.get(0);
        }
        return new PaperWithUserReview(currentUserReview,pPaper);
    }


}
