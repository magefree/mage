package mage.cards.m;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.combat.ReselectDefenderAttackedByTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.target.common.TargetAttackingCreature;


/**
 *
 * @author Xanderhall
 */
public final class MisleadingSignpost extends CardImpl {

    public MisleadingSignpost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}");
        

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Misleading Signpost enters the battlefield during the declare attackers step, you may reselect which player or permanent target attacking creature is attacking.
        Ability ability = new ConditionalTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new ReselectDefenderAttackedByTargetEffect(true), true),
                new IsStepCondition(PhaseStep.DECLARE_ATTACKERS, false),
                "When {this} enters the battlefield during the declare attackers step, you may reselect which player or permanent target attacking creature is attacking. "
                + "<i>(It can't attack its controller or their permanents)</i>");
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