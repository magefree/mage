
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author LoneFox
 */
public final class RootElemental extends CardImpl {

    public RootElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Morph {5}{G}{G}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{5}{G}{G}")));
        // When Root Elemental is turned face up, you may put a creature card from your hand onto the battlefield.
        this.addAbility(new TurnedFaceUpSourceTriggeredAbility(new PutCardFromHandOntoBattlefieldEffect(StaticFilters.FILTER_CARD_CREATURE_A)));
    }

    private RootElemental(final RootElemental card) {
        super(card);
    }

    @Override
    public RootElemental copy() {
        return new RootElemental(this);
    }
}
