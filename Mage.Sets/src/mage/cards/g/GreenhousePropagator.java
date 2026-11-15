package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class GreenhousePropagator extends CardImpl {

    public GreenhousePropagator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever another creature you control enters, you gain 1 life.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
            new GainLifeEffect(1), StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL
        ));

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());
    }

    private GreenhousePropagator(final GreenhousePropagator card) {
        super(card);
    }

    @Override
    public GreenhousePropagator copy() {
        return new GreenhousePropagator(this);
    }
}
