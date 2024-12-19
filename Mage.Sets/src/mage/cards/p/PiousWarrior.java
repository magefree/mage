package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.DealtCombatDamageToSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Backfir3
 */
public final class PiousWarrior extends CardImpl {

    public PiousWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever Pious Warrior is dealt combat damage, you gain that much life.
        this.addAbility(new DealtCombatDamageToSourceTriggeredAbility(new GainLifeEffect(SavedDamageValue.MUCH), false));
    }

    private PiousWarrior(final PiousWarrior card) {
        super(card);
    }

    @Override
    public PiousWarrior copy() {
        return new PiousWarrior(this);
    }
}
