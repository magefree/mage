package mage.cards.b;

import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BondOfFlourishing extends CardImpl {

    private static final FilterCard filter = new FilterPermanentCard("a permanent card");

    public BondOfFlourishing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Look at the top three card of your library. You may reveal a permanent card from among them and put it into your hand. Put the rest on the bottom of your library in any order. You gain 3 life.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                StaticValue.get(3), false,
                StaticValue.get(1), filter, false
        ));
        this.getSpellAbility().addEffect(new GainLifeEffect(3).setText("You gain 3 life."));
    }

    private BondOfFlourishing(final BondOfFlourishing card) {
        super(card);
    }

    @Override
    public BondOfFlourishing copy() {
        return new BondOfFlourishing(this);
    }
}
