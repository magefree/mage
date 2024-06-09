package mage.cards.g;

import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author North
 */
public final class GraveExchange extends CardImpl {

    public GraveExchange(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{B}");

        // Return target creature card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));

        // Target player sacrifices a creature.
        this.getSpellAbility().addEffect(new SacrificeEffect(StaticFilters.FILTER_PERMANENT_CREATURE, 1, "Target player")
                .setTargetPointer(new SecondTargetPointer()));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private GraveExchange(final GraveExchange card) {
        super(card);
    }

    @Override
    public GraveExchange copy() {
        return new GraveExchange(this);
    }
}
