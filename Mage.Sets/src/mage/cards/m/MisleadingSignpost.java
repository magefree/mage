package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.effects.common.combat.ReselectDefenderAttackedByTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.target.common.TargetAttackingCreature;

import java.util.UUID;

/**
 * @author Xanderhall
 */
public final class MisleadingSignpost extends CardImpl {

    private static final Condition condition = new IsStepCondition(PhaseStep.DECLARE_ATTACKERS, false);

    public MisleadingSignpost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}");

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Misleading Signpost enters the battlefield during the declare attackers step, you may reselect which player or permanent target attacking creature is attacking.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new ReselectDefenderAttackedByTargetEffect(true), true
        ).withTriggerCondition(condition);
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(ability);

        // {T}: Add {U}.
        this.addAbility(new BlueManaAbility());
    }

    private MisleadingSignpost(final MisleadingSignpost card) {
        super(card);
    }

    @Override
    public MisleadingSignpost copy() {
        return new MisleadingSignpost(this);
    }
}
