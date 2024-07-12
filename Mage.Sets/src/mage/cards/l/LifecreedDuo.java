package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
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
public final class LifecreedDuo extends CardImpl {

    public LifecreedDuo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.BAT);
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever another creature you control enters, you gain 1 life.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new GainLifeEffect(1), StaticFilters.FILTER_ANOTHER_CREATURE
        ));
    }

    private LifecreedDuo(final LifecreedDuo card) {
        super(card);
    }

    @Override
    public LifecreedDuo copy() {
        return new LifecreedDuo(this);
    }
}
