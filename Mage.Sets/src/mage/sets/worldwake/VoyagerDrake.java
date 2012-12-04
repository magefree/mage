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
package mage.sets.worldwake;

import java.util.List;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MultikickerAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class VoyagerDrake extends CardImpl<VoyagerDrake> {

    public VoyagerDrake(UUID ownerId) {
        super(ownerId, 45, "Voyager Drake", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.expansionSetCode = "WWK";
        this.subtype.add("Drake");

        this.color.setBlue(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Multikicker {U}
        this.addAbility(new MultikickerAbility(new ManaCostsImpl("{U}")));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Voyager Drake enters the battlefield, up to X target creatures gain flying until end of turn, where X is the number of times Voyager Drake was kicked.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new VoyagerDrakeEffect(), false));
    }

    public VoyagerDrake(final VoyagerDrake card) {
        super(card);
    }

    @Override
    public VoyagerDrake copy() {
        return new VoyagerDrake(this);
    }
}

class VoyagerDrakeEffect extends OneShotEffect<VoyagerDrakeEffect> {

    public VoyagerDrakeEffect() {
        super(Constants.Outcome.PutCreatureInPlay);
        this.staticText = "up to X target creatures gain flying until end of turn, where X is the number of times {this} was kicked";
    }

    public VoyagerDrakeEffect(final VoyagerDrakeEffect effect) {
        super(effect);
    }

    @Override
    public VoyagerDrakeEffect copy() {
        return new VoyagerDrakeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new CardTypePredicate(CardType.CREATURE));
        Player you = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            for (Ability ability : permanent.getAbilities()) {
                if (ability instanceof MultikickerAbility) {
                    int count = Math.min(game.getBattlefield().countAll(filter, game), ((MultikickerAbility) ability).getActivateCount());
                    TargetCreaturePermanent target = new TargetCreaturePermanent(0, count, filter, false);
                    if (you != null) {
                        if (target.canChoose(source.getControllerId(), game) && target.choose(Constants.Outcome.AddAbility, source.getControllerId(), source.getId(), game)) {
                            if (!target.getTargets().isEmpty()) {
                                List<UUID> targets = target.getTargets();
                                for (UUID targetId : targets) {
                                    Permanent creature = game.getPermanent(targetId);
                                    if (creature != null) {
                                        ContinuousEffect effect = new GainAbilityTargetEffect(FlyingAbility.getInstance(), Constants.Duration.EndOfTurn);
                                        effect.setTargetPointer(new FixedTarget(creature.getId()));
                                        game.addEffect(effect, source);
                                    }
                                }
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
