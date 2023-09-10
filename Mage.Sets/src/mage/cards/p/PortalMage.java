
package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.ReselectDefenderAttackedByTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterAttackingCreature;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAttackingCreature;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetDefender;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class PortalMage extends CardImpl {

    public PortalMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Portal Mage enters the battlefield during the declare attackers step, you may reselect which player or planeswalker target attacking creature is attacking.
        Ability ability = new ConditionalTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new ReselectDefenderAttackedByTargetEffect(true), true),
                new IsStepCondition(PhaseStep.DECLARE_ATTACKERS, false),
                "When {this} enters the battlefield during the declare attackers step, you may reselect which player or planeswalker target attacking creature is attacking. "
                + "<i>(It can't attack its controller or its controller's planeswalkers.)</i>");
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(ability);
    }

    private PortalMage(final PortalMage card) {
        super(card);
    }

    @Override
    public PortalMage copy() {
        return new PortalMage(this);
    }
}