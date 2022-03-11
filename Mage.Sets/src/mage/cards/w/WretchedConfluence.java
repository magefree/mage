
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class WretchedConfluence extends CardImpl {

    public WretchedConfluence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}{B}");

        // Choose three. You may choose the same mode more than once.
        this.getSpellAbility().getModes().setMinModes(3);
        this.getSpellAbility().getModes().setMaxModes(3);
        this.getSpellAbility().getModes().setEachModeMoreThanOnce(true);

        // - Target player draws a card and loses 1 life;
        Effect effect = new LoseLifeTargetEffect(1);
        effect.setText("and loses 1 life");
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(1));
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetPlayer());

        // Target creature gets -2/-2 until end of turn;
        Mode mode = new Mode(new BoostTargetEffect(-2, -2, Duration.EndOfTurn));
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().getModes().addMode(mode);

        // Return target creature card from your graveyard to your hand.
        mode = new Mode(new ReturnFromGraveyardToHandTargetEffect());
        mode.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.getSpellAbility().getModes().addMode(mode);
    }

    private WretchedConfluence(final WretchedConfluence card) {
        super(card);
    }

    @Override
    public WretchedConfluence copy() {
        return new WretchedConfluence(this);
    }
}
