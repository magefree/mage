package mage.cards.t;

import java.util.Objects;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EndOfCombatTriggeredAbility;
import mage.abilities.condition.common.SourceOnBattlefieldControlUnchangedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.BlockedAttackerWatcher;
import mage.watchers.common.LostControlWatcher;

/**
 *
 * @author jeffwadsworth
 *
 * 5/1/2009 The ability grants you control of all creatures that are blocking it
 * as the ability resolves. This will include any creatures that were put onto
 * the battlefield blocking it.
 *
 * 5/1/2009 Any blocking creatures that regenerated during combat will have been
 * removed from combat. Since such creatures are no longer in combat, they
 * cannot be blocking The Wretched, which means you won't be able to gain
 * control of them.
 *
 * 5/1/2009 If The Wretched itself regenerated during combat, then it will have
 * been removed from combat. Since it is no longer in combat, there cannot be
 * any creatures blocking it, which means you won't be able to gain control of
 * any creatures.
 *
 * 10/1/2009 The Wretched's ability triggers only if it's still on the
 * battlefield when the end of combat step begins (after the combat damage
 * step). For example, if it's blocked by a 7/7 creature and is destroyed, its
 * ability won't trigger at all.
 *
 * 10/1/2009 If The Wretched leaves the battlefield, you no longer control it,
 * so the duration of its control-change effect ends.
 *
 * 10/1/2009 If you lose control of The Wretched before its ability resolves,
 * you won't gain control of the creatures blocking it at all.
 *
 * 10/1/2009 Once the ability resolves, it doesn't care whether the permanents
 * you gained control of remain creatures, only that they remain on the
 * battlefield.
 */
public final class TheWretched extends CardImpl {

    public TheWretched(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // At end of combat, gain control of all creatures blocking The Wretched for as long as you control The Wretched.
        Ability ability = new EndOfCombatTriggeredAbility(new TheWretchedEffect(), false);
        this.addAbility(ability, new BlockedAttackerWatcher());
        ability.addWatcher(new LostControlWatcher());
    }

    private TheWretched(final TheWretched card) {
        super(card);
    }

    @Override
    public TheWretched copy() {
        return new TheWretched(this);
    }
}

class TheWretchedEffect extends OneShotEffect {

    TheWretchedEffect() {
        super(Outcome.Benefit);
        staticText = "gain control of all creatures blocking {this} for as long as you control {this}";
    }

    TheWretchedEffect(final TheWretchedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent theWretched = (Permanent) source.getSourceObjectIfItStillExists(game);
        if (theWretched == null) {
            return false;
        }
        if (theWretched.isRemovedFromCombat() || !theWretched.isAttacking()) {
            return false;
        }
        // Check if control of source has changed since ability triggered????? (does it work is it neccessary???)
        Permanent permanent = game.getBattlefield().getPermanent(source.getSourceId());
        if (permanent != null && !Objects.equals(permanent.getControllerId(), source.getControllerId())) {
            return false;
        }

        for (CombatGroup combatGroup : game.getCombat().getGroups()) {
            if (combatGroup.getAttackers().contains(source.getSourceId())) {
                for (UUID creatureId : combatGroup.getBlockers()) {
                    Permanent blocker = game.getPermanent(creatureId);
                    if (blocker != null
                            && blocker.getBlocking() > 0) {
                        ContinuousEffect effect = new ConditionalContinuousEffect(
                                new GainControlTargetEffect(Duration.Custom, source.getControllerId()),
                                new SourceOnBattlefieldControlUnchangedCondition(), "");
                        effect.setTargetPointer(new FixedTarget(blocker.getId(), game));
                        game.addEffect(effect, source);

                    }
                }
            }
        }
        return true;
    }

    @Override
    public TheWretchedEffect copy() {
        return new TheWretchedEffect(this);
    }
}
