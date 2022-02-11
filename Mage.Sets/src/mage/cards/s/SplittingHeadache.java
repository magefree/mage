package mage.cards.s;

import mage.abilities.Mode;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class SplittingHeadache extends CardImpl {

    public SplittingHeadache(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");


        // Choose one —
        // • Target player discards two cards.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new DiscardTargetEffect(2));

        // • Target player reveals their hand. You choose a card from it. That player discards that card.
        Mode mode = new Mode();
        mode.addEffect(new DiscardCardYouChooseTargetEffect(TargetController.ANY));
        mode.addTarget(new TargetPlayer());
        this.getSpellAbility().addMode(mode);
    }

    private SplittingHeadache(final SplittingHeadache card) {
        super(card);
    }

    @Override
    public SplittingHeadache copy() {
        return new SplittingHeadache(this);
    }
}
