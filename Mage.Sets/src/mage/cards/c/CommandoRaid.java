package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.DamagedPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class CommandoRaid extends CardImpl {

    public CommandoRaid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Until end of turn, target creature you control gains "When this creature deals combat damage to a player, you may have it deal damage equal to its power to target creature that player controls."
        Effect effect = new DamageTargetEffect(new SourcePermanentPowerCount());
        effect.setText("have it deal damage equal to its power to target creature that player controls.");
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(effect, true, true);
        ability.addTarget(new TargetCreaturePermanent());
        ability.setTargetAdjuster(new DamagedPlayerControlsTargetAdjuster());

        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                ability, Duration.EndOfTurn,
                "Until end of turn, target creature you control gains "
                        + "\"When this creature deals combat damage to a player, "
                        + "you may have it deal damage equal to its power "
                        + "to target creature that player controls.\""
        ));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private CommandoRaid(final CommandoRaid card) {
        super(card);
    }

    @Override
    public CommandoRaid copy() {
        return new CommandoRaid(this);
    }
}
