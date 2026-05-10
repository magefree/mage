package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpAllTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.DisguiseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanentThisOrAnother;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author greenlovecat
 */

public final class TrueIdentity extends CardImpl {
    
    private static final FilterPermanentThisOrAnother filter = new FilterPermanentThisOrAnother(StaticFilters.FILTER_CONTROLLED_PERMANENT, true);

    public TrueIdentity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // Whenever this enchantment or another permanent you control is turned face up, scry 1, then draw a card. This ability triggers only once each turn.
        Ability ability = new TurnedFaceUpAllTriggeredAbility(new ScryEffect(1, false), filter, true).setTriggersLimitEachTurn(1);
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy(", then"));
        this.addAbility(ability);

        // Disguise {W}
        this.addAbility(new DisguiseAbility(this, new ManaCostsImpl<>("{W}")));
    }

    private TrueIdentity(final TrueIdentity card) {
        super(card);
    }

    @Override
    public TrueIdentity copy() {
        return new TrueIdentity(this);
    }

}
