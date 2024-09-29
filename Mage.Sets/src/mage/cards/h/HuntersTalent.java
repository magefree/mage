package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainClassAbilitySourceEffect;
import mage.abilities.hint.common.FerociousHint;
import mage.abilities.keyword.ClassLevelAbility;
import mage.abilities.keyword.ClassReminderAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetAttackingCreature;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HuntersTalent extends CardImpl {

    public HuntersTalent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        this.subtype.add(SubType.CLASS);

        // (Gain the next level as a sorcery to add its ability.)
        this.addAbility(new ClassReminderAbility());

        // When Hunter's Talent enters, target creature you control deals damage equal to its power to target creature you don't control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageWithPowerFromOneToAnotherTargetEffect());
        ability.addTarget(new TargetControlledCreaturePermanent());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.addAbility(ability);

        // {1}{G}: Level 2
        this.addAbility(new ClassLevelAbility(2, "{1}{G}"));

        // Whenever you attack, target attacking creature gets +1/+0 and gains trample until end of turn.
        ability = new AttacksWithCreaturesTriggeredAbility(new BoostTargetEffect(1, 0)
                .setText("target attacking creature gets +1/+0"), 1);
        ability.addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance())
                .setText("and gains trample until end of turn"));
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(ability, 2)));

        // {3}{G}: Level 3
        this.addAbility(new ClassLevelAbility(3, "{3}{G}"));

        // At the beginning of your end step, if you control a creature with power 4 or greater, draw a card.
        this.addAbility(new SimpleStaticAbility(
                new GainClassAbilitySourceEffect(new BeginningOfEndStepTriggeredAbility(
                        new DrawCardSourceControllerEffect(1), TargetController.YOU,
                        FerociousCondition.instance, false
                ), 3)
        ).addHint(FerociousHint.instance));
    }

    private HuntersTalent(final HuntersTalent card) {
        super(card);
    }

    @Override
    public HuntersTalent copy() {
        return new HuntersTalent(this);
    }
}
