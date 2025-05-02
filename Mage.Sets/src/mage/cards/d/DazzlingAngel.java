package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DazzlingAngel extends CardImpl {

    public DazzlingAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever another creature you control enters, you gain 1 life.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new GainLifeEffect(1), StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL
        ));
    }

    private DazzlingAngel(final DazzlingAngel card) {
        super(card);
    }

    @Override
    public DazzlingAngel copy() {
        return new DazzlingAngel(this);
    }
}
