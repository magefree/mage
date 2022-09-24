package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EndOfCombatTriggeredAbility;
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

import java.util.UUID;

/**
 * @author jeffwadsworth
 * <p>
 * 5/1/2009 The ability grants you control of all creatures that are blocking it
 * as the ability resolves. This will include any creatures that were put onto
 * the battlefield blocking it.
 * <p>
 * 5/1/2009 Any blocking creatures that regenerated during combat will have been
 * removed from combat. Since such creatures are no longer in combat, they
 * cannot be blocking The Wretched, which means you won't be able to gain
 * control of them.
 * <p>
 * 5/1/2009 If The Wretched itself regenerated during combat, then it will have
 * been removed from combat. Since it is no longer in combat, there cannot be
 * any creatures blocking it, which means you won't be able to gain control of
 * any creatures.
 * <p>
 * 10/1/2009 The Wretched's ability triggers only if it's still on the
 * battlefield when the end of combat step begins (after the combat damage
 * step). For example, if it's blocked by a 7/7 creature and is destroyed, its
 * ability won't trigger at all.
 * <p>
 * 10/1/2009 If The Wretched leaves the battlefield, you no longer control it,
 * so the duration of its control-change effect ends.
 * <p>
 * 10/1/2009 If you lose control of The Wretched before its ability resolves,
 * you won't gain control of the creatures blocking it at all.
 * <p>
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
        this.addAbility(new EndOfCombatTriggeredAbility(new TheWretchedEffect(), false), new BlockedAttackerWatcher());
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
        Permanent theWretched = source.getSourcePermanentIfItStillExists(game);
        if (theWretched == null
                || !theWretched.isAttacking()
                || !source.isControlledBy(theWretched.getControllerId())) {
            return false;
        }
        // Check if control of source has changed since ability triggered????? (does it work is it neccessary???)
        for (CombatGroup combatGroup : game.getCombat().getGroups()) {
            if (!combatGroup.getAttackers().contains(source.getSourceId())) {
                continue;
            }
            for (UUID creatureId : combatGroup.getBlockers()) {
                Permanent blocker = game.getPermanent(creatureId);
                if (blocker == null
                        || blocker.getBlocking() <= 0) {
                    continue;
                }
                ContinuousEffect effect = new GainControlTargetEffect(Duration.WhileControlled, source.getControllerId());
                effect.setTargetPointer(new FixedTarget(blocker.getId(), game));
                game.addEffect(effect, source);
            }
        }
        return true;
    }

    @Override
    public TheWretchedEffect copy() {
        return new TheWretchedEffect(this);
    }
}
