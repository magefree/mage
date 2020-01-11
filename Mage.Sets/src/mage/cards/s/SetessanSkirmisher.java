package mage.cards.s;

import mage.MageInt;
import mage.abilities.abilityword.ConstellationAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SetessanSkirmisher extends CardImpl {

    public SetessanSkirmisher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Constellation — Whenever an enchantment enters the battlefield under your control, Setessan Skirmisher gets +1/+1 until end of turn.
        this.addAbility(new ConstellationAbility(
                new BoostSourceEffect(1, 1, Duration.EndOfTurn), false, false
        ));
    }

    private SetessanSkirmisher(final SetessanSkirmisher card) {
        super(card);
    }

    @Override
    public SetessanSkirmisher copy() {
        return new SetessanSkirmisher(this);
    }
}
