package mage.cards.x;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.LoseLifePermanentControllerEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

import java.util.Objects;
import java.util.UUID;

import static mage.constants.Outcome.Benefit;


/**
 * @author jesusjbr
 */

public final class XantchaSleeperAgent extends CardImpl {

    public XantchaSleeperAgent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.MINION);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // As Xantcha, Sleeper Agent enters the battlefield, an opponent of your choice gains control of it.
        this.addAbility(new AsEntersBattlefieldAbility(new XantchaSleeperAgentChangeControlEffect()));

        // Xantcha attacks each combat if able and can’t attack its owner or planeswalkers its owner controls.
        Ability ability = new AttacksEachCombatStaticAbility();
        ability.addEffect(new XantchaSleeperAgentAttackRestrictionEffect());
        this.addAbility(ability);

        // {3}: Xantcha’s controller loses 2 life and you draw a card. Any player may activate this ability.
        SimpleActivatedAbility simpleAbility = new SimpleActivatedAbility(
                new LoseLifePermanentControllerEffect(2)
                        .setText("{this}’s controller loses 2 life"),
                new GenericManaCost(3)
        );
        simpleAbility.addEffect(new DrawCardSourceControllerEffect(1).setText("and you draw a card"));
        simpleAbility.addEffect(new InfoEffect("Any player may activate this ability"));
        simpleAbility.setMayActivate(TargetController.ANY);
        this.addAbility(simpleAbility);
    }

    private XantchaSleeperAgent(final XantchaSleeperAgent card) {
        super(card);
    }

    @Override
    public XantchaSleeperAgent copy() {
        return new XantchaSleeperAgent(this);
    }
}

class XantchaSleeperAgentChangeControlEffect extends OneShotEffect {

    XantchaSleeperAgentChangeControlEffect() {
        super(Benefit);
        staticText = "an opponent of your choice gains control of it.";
    }

    private XantchaSleeperAgentChangeControlEffect(final XantchaSleeperAgentChangeControlEffect effect) {
        super(effect);
    }

    @Override
    public XantchaSleeperAgentChangeControlEffect copy() {
        return new XantchaSleeperAgentChangeControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Target target = new TargetOpponent();
        target.setNotTarget(true);
        if (!controller.choose(Outcome.Benefit, target, source.getSourceId(), game)) {
            return false;
        }
        Player player = game.getPlayer(target.getFirstTarget());
        if (player == null) {
            return false;
        }
        ContinuousEffect continuousEffect = new GainControlTargetEffect(
                Duration.WhileOnBattlefield, true, player.getId()
        );
        continuousEffect.setTargetPointer(new FixedTarget(
                source.getSourceId(), source.getSourceObjectZoneChangeCounter()
        ));
        game.addEffect(continuousEffect, source);
        return true;
    }
}

class XantchaSleeperAgentAttackRestrictionEffect extends RestrictionEffect {

    XantchaSleeperAgentAttackRestrictionEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "and can't attack its owner or planeswalkers its owner controls.";
    }

    private XantchaSleeperAgentAttackRestrictionEffect(final XantchaSleeperAgentAttackRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public XantchaSleeperAgentAttackRestrictionEffect copy() {
        return new XantchaSleeperAgentAttackRestrictionEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return Objects.equals(permanent.getId(), source.getSourceId());
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        if (defenderId == null) {
            return true;
        }

        boolean allowAttack = true;
        UUID ownerPlayerId = source.getSourcePermanentIfItStillExists(game).getOwnerId();

        if (defenderId.equals(ownerPlayerId)) {
            allowAttack = false;
        } else {
            Permanent planeswalker = game.getPermanent(defenderId);
            if (planeswalker != null && planeswalker.isControlledBy(ownerPlayerId)) {
                allowAttack = false;
            }
        }
        return allowAttack;
    }
}
