package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterArtifactPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetAttackingCreature;
import mage.target.targetadjustment.DamagedPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class DreadmawsIre extends CardImpl {

    private static final FilterArtifactPermanent filter
            = new FilterArtifactPermanent("artifact that player controls");

    public DreadmawsIre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Until end of turn, target attacking creature gets +2/+2 and gains trample and "Whenever this creature deals combat damage to a player, destroy target artifact that player controls."
        this.getSpellAbility().addTarget(new TargetAttackingCreature());
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2)
                .setText("Until end of turn, target attacking creature gets +2/+2"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance())
                .setText("and gains trample"));

        Effect effect = new DamageTargetEffect(new SourcePermanentPowerCount());
        effect.setText("have it deal damage equal to its power to target creature that player controls.");
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new DestroyTargetEffect(), false, true);
        ability.addTarget(new TargetPermanent(filter));
        ability.setTargetAdjuster(new DamagedPlayerControlsTargetAdjuster());

        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(ability)
                .setText("and \"Whenever this creature deals combat damage to a player, destroy target artifact that player controls.\""));
    }

    private DreadmawsIre(final DreadmawsIre card) {
        super(card);
    }

    @Override
    public DreadmawsIre copy() {
        return new DreadmawsIre(this);
    }
}
