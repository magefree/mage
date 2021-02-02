package mage.cards.d;

import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.DelveAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class DigThroughTime extends CardImpl {

    public DigThroughTime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{6}{U}{U}");

        // Delve
        this.addAbility(new DelveAbility());

        // Look at the top seven cards of your library. Put two of them into your hand and the rest on the bottom of your library in any order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(StaticValue.get(7), false, StaticValue.get(2), new FilterCard(), Zone.LIBRARY, false, false));
    }

    private DigThroughTime(final DigThroughTime card) {
        super(card);
    }

    @Override
    public DigThroughTime copy() {
        return new DigThroughTime(this);
    }
}
