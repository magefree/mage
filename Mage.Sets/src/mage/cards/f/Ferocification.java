package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Ferocification extends CardImpl {

    public Ferocification(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // At the beginning of combat on your turn, choose one--
        // * Target creature you control gets +2/+0 until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new BoostTargetEffect(2, 0), TargetController.YOU, false
        );
        ability.addTarget(new TargetControlledCreaturePermanent());

        // * Target creature you control gains menace and haste until end of turn.
        ability.addMode(new Mode(new GainAbilityTargetEffect(new MenaceAbility()).setText("target creature you control gains menace"))
                .addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance()).setText("and haste until end of turn"))
                .addTarget(new TargetControlledCreaturePermanent()));
        this.addAbility(ability);
    }

    private Ferocification(final Ferocification card) {
        super(card);
    }

    @Override
    public Ferocification copy() {
        return new Ferocification(this);
    }
}
