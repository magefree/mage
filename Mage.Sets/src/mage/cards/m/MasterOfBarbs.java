package mage.cards.m;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.OpponentDealtNoncombatDamageTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

/**
 *
 * @author muz
 */
public final class MasterOfBarbs extends CardImpl {

    public MasterOfBarbs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever one or more opponents are dealt noncombat damage, creatures you control get +1/+0 until end of turn.
        this.addAbility(new OpponentDealtNoncombatDamageTriggeredAbility(
            new BoostControlledEffect(1, 0, Duration.EndOfTurn)
        ));
    }

    private MasterOfBarbs(final MasterOfBarbs card) {
        super(card);
    }

    @Override
    public MasterOfBarbs copy() {
        return new MasterOfBarbs(this);
    }
}
