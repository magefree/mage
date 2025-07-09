package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DebrisFieldCrusher extends CardImpl {

    public DebrisFieldCrusher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}{R}");

        this.subtype.add(SubType.SPACECRAFT);

        // When this Spacecraft enters, it deals 3 damage to any target.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(3));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // Station
        this.addAbility(new StationAbility());

        // STATION 8+
        // Flying
        // {1}{R}: This Spacecraft gets +2/+0 until end of turn.
        // 1/5
        this.addAbility(new StationLevelAbility(8)
                .withLevelAbility(FlyingAbility.getInstance())
                .withLevelAbility(new SimpleActivatedAbility(
                        new BoostSourceEffect(2, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{1}{R}")
                ))
                .withPT(1, 5));
    }

    private DebrisFieldCrusher(final DebrisFieldCrusher card) {
        super(card);
    }

    @Override
    public DebrisFieldCrusher copy() {
        return new DebrisFieldCrusher(this);
    }
}
