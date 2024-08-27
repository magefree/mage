package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MoonriseCleric extends CardImpl {

    public MoonriseCleric(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W/B}{W/B}");

        this.subtype.add(SubType.BAT);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Moonrise Cleric attacks, you gain 1 life.
        this.addAbility(new AttacksTriggeredAbility(new GainLifeEffect(1)));
    }

    private MoonriseCleric(final MoonriseCleric card) {
        super(card);
    }

    @Override
    public MoonriseCleric copy() {
        return new MoonriseCleric(this);
    }
}
