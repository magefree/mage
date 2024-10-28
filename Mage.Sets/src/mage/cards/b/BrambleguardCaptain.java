package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrambleguardCaptain extends CardImpl {

    public BrambleguardCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.MOUSE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // At the beginning of combat on your turn, target creature you control gets +X/+0 until end of turn, where X is Brambleguard Captain's power.
        Ability ability = new BeginningOfCombatTriggeredAbility(new BoostTargetEffect(
                SourcePermanentPowerValue.NOT_NEGATIVE, StaticValue.get(0), Duration.EndOfTurn
        ), false);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private BrambleguardCaptain(final BrambleguardCaptain card) {
        super(card);
    }

    @Override
    public BrambleguardCaptain copy() {
        return new BrambleguardCaptain(this);
    }
}
