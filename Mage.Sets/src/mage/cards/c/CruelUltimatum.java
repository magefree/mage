package mage.cards.c;

import mage.abilities.effects.common.*;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author North
 */
public final class CruelUltimatum extends CardImpl {

    public CruelUltimatum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}{U}{B}{B}{B}{R}{R}");

        // Target opponent sacrifices a creature, discards three cards, then loses 5 life.
        // You return a creature card from your graveyard to your hand, draw three cards, then gain 5 life.
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new SacrificeEffect(
                StaticFilters.FILTER_PERMANENT_CREATURE,
                1, "Target opponent"
        ));
        this.getSpellAbility().addEffect(new DiscardTargetEffect(3).setText(", discards three cards"));
        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(5).setText(", then loses 5 life"));
        this.getSpellAbility().addEffect(new ReturnCardChosenFromGraveyardEffect(
                false, StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD, PutCards.HAND
        ).concatBy("You"));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3).concatBy(","));
        this.getSpellAbility().addEffect(new GainLifeEffect(5).setText(", then gain 5 life"));
    }

    private CruelUltimatum(final CruelUltimatum card) {
        super(card);
    }

    @Override
    public CruelUltimatum copy() {
        return new CruelUltimatum(this);
    }
}
