
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class RocketTrooper extends CardImpl {

    public RocketTrooper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.TROOPER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Trooper creatures you control have "Whenever this creature enters the battlefield, it deals 1 damage to target creature an opponent controls".
        Effect effect = new DamageTargetEffect(1);
        effect.setText("Whenever this creature enters the battlefield, it deals 1 damage to target creature an opponent controls");
        Ability ability = new EntersBattlefieldTriggeredAbility(effect, false, true);
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityControlledEffect(ability, Duration.WhileOnBattlefield,
                        new FilterCreaturePermanent(SubType.TROOPER, "Trooper creatures"), false)));

    }

    private RocketTrooper(final RocketTrooper card) {
        super(card);
    }

    @Override
    public RocketTrooper copy() {
        return new RocketTrooper(this);
    }
}
