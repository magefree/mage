package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.CycleControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DrannithHealer extends CardImpl {

    public DrannithHealer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cycle another card, you gain 1 life.
        this.addAbility(new CycleControllerTriggeredAbility(new GainLifeEffect(1), false, true));

        // Cycling {1}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{1}")));
    }

    private DrannithHealer(final DrannithHealer card) {
        super(card);
    }

    @Override
    public DrannithHealer copy() {
        return new DrannithHealer(this);
    }
}
