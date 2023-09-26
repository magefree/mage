package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class RocketTrooper extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.TROOPER, "Trooper creatures");

    public RocketTrooper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.TROOPER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Trooper creatures you control have "Whenever this creature enters the battlefield, it deals 1 damage to target creature an opponent controls".
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(1, "it"), false)
                .setTriggerPhrase("When this creature enters the battlefield, ");
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(ability, Duration.WhileOnBattlefield, filter, false)));

    }

    private RocketTrooper(final RocketTrooper card) {
        super(card);
    }

    @Override
    public RocketTrooper copy() {
        return new RocketTrooper(this);
    }
}
