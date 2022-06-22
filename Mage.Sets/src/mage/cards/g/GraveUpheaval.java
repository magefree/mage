package mage.cards.g;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnCreatureFromGraveyardToBattlefieldAndGainHasteEffect;
import mage.abilities.keyword.BasicLandcyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class GraveUpheaval extends CardImpl {

    public GraveUpheaval(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{R}");

        // Put target creature card from a graveyard onto the battlefield under your control. It gains haste.
        this.getSpellAbility().addEffect(new ReturnCreatureFromGraveyardToBattlefieldAndGainHasteEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(StaticFilters.FILTER_CARD_CREATURE));

        // Basic landcycling {2}
        this.addAbility(new BasicLandcyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private GraveUpheaval(final GraveUpheaval card) {
        super(card);
    }

    @Override
    public GraveUpheaval copy() {
        return new GraveUpheaval(this);
    }
}

