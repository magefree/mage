package mage.cards.w;

import mage.abilities.Mode;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class WretchedConfluence extends CardImpl {

    public WretchedConfluence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}{B}");

        // Choose three. You may choose the same mode more than once.
        this.getSpellAbility().getModes().setMinModes(3);
        this.getSpellAbility().getModes().setMaxModes(3);
        this.getSpellAbility().getModes().setMayChooseSameModeMoreThanOnce(true);

        // - Target player draws a card and loses 1 life;
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(1));
        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(1).withTargetDescription("and"));
        this.getSpellAbility().addTarget(new TargetPlayer());

        // Target creature gets -2/-2 until end of turn;
        this.getSpellAbility().getModes().addMode(new Mode(new BoostTargetEffect(-2, -2))
                .addTarget(new TargetCreaturePermanent()));

        // Return target creature card from your graveyard to your hand.
        this.getSpellAbility().getModes().addMode(new Mode(new ReturnFromGraveyardToHandTargetEffect())
                .addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD)));
    }

    private WretchedConfluence(final WretchedConfluence card) {
        super(card);
    }

    @Override
    public WretchedConfluence copy() {
        return new WretchedConfluence(this);
    }
}
