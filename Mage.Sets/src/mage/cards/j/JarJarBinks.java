/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.starwars;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.TargetController;
import mage.filter.Filter;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.RandomUtil;

/**
 *
 * @author Styxo
 */
public class JarJarBinks extends CardImpl {

    public JarJarBinks(UUID ownerId) {
        super(ownerId, 41, "Jar Jar Binks", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.expansionSetCode = "SWS";
        this.supertype.add("Legendary");
        this.subtype.add("Gungan");
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Jar jar Binks can't block.
        this.addAbility(new CantBlockAbility());

        // When Jar Jar Binks enter the battlefield, an opponent gains control of it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new JarJarBinksEffect()));

        // At the beggining of combat on your turn, tap the creature you control with the highest power. If two or more creatures are tied for the greatest power, you choose one of them.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new JarJarBinksTapEffect(), TargetController.YOU, false));
    }

    public JarJarBinks(final JarJarBinks card) {
        super(card);
    }

    @Override
    public JarJarBinks copy() {
        return new JarJarBinks(this);
    }
}

class JarJarBinksEffect extends OneShotEffect {

    public JarJarBinksEffect() {
        super(Outcome.GainControl);
        this.staticText = "an opponent gains control of it";
    }

    public JarJarBinksEffect(final JarJarBinksEffect effect) {
        super(effect);
    }

    @Override
    public JarJarBinksEffect copy() {
        return new JarJarBinksEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent jarJar = (Permanent) source.getSourceObjectIfItStillExists(game);
        List<UUID> list = new ArrayList<UUID>(game.getOpponents(source.getControllerId()));
        UUID player = list.get(RandomUtil.nextInt(list.size()));
        if (player != null && jarJar != null) {
            ContinuousEffect effect = new JarJarBinksGainControlSourceEffect();
            effect.setTargetPointer(new FixedTarget(player));
            game.addEffect(effect, source);
            game.informPlayers(jarJar.getName() + " is now controlled by " + game.getPlayer(player).getLogName());
            return true;
        }
        return false;
    }
}

class JarJarBinksGainControlSourceEffect extends ContinuousEffectImpl {

    public JarJarBinksGainControlSourceEffect() {
        super(Duration.Custom, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
    }

    public JarJarBinksGainControlSourceEffect(final JarJarBinksGainControlSourceEffect effect) {
        super(effect);
    }

    @Override
    public JarJarBinksGainControlSourceEffect copy() {
        return new JarJarBinksGainControlSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID targetOpponent = getTargetPointer().getFirst(game, source);
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && targetOpponent != null) {
            permanent.changeControllerId(targetOpponent, game);
        } else {
            // no valid target exists, effect can be discarded
            discard();
        }
        return true;
    }
}

class JarJarBinksTapEffect extends OneShotEffect {

    public JarJarBinksTapEffect() {
        super(Outcome.Tap);
        this.staticText = "tap the creature you control with the highest power. If two or more creatures are tied for the greatest power, you choose one of them";
    }

    public JarJarBinksTapEffect(final JarJarBinksTapEffect effect) {
        super(effect);
    }

    @Override
    public JarJarBinksTapEffect copy() {
        return new JarJarBinksTapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            int highestPower = Integer.MIN_VALUE;
            boolean multipleExist = false;
            Permanent permanentToTap = null;
            for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterControlledCreaturePermanent(), source.getControllerId(), game)) {
                if (permanent.getPower().getValue() > highestPower) {
                    permanentToTap = permanent;
                    highestPower = permanent.getPower().getValue();
                    multipleExist = false;
                } else if (permanent.getPower().getValue() == highestPower) {
                    multipleExist = true;
                }
            }
            if (multipleExist) {
                FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("one of the creatures with the highest power");
                filter.add(new PowerPredicate(Filter.ComparisonType.Equal, highestPower));
                Target target = new TargetPermanent(filter);
                target.setNotTarget(true);
                if (target.canChoose(source.getSourceId(), source.getControllerId(), game)) {
                    if (controller.choose(outcome, target, source.getSourceId(), game)) {
                        permanentToTap = game.getPermanent(target.getFirstTarget());
                    }
                }
            }
            if (permanentToTap != null) {
                game.informPlayers(new StringBuilder(sourcePermanent.getName()).append(" chosen creature: ").append(permanentToTap.getName()).toString());
                return permanentToTap.tap(game);
            }
            return true;
        }

        return false;
    }
}
