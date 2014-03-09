/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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

package mage.sets.scarsofmirrodin;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.token.MyrToken;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class MyrBattlesphere extends CardImpl<MyrBattlesphere> {

    public MyrBattlesphere(UUID ownerId) {
        super(ownerId, 180, "Myr Battlesphere", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{7}");
        this.expansionSetCode = "SOM";
        this.subtype.add("Myr");
        this.subtype.add("Construct");
        this.power = new MageInt(4);
        this.toughness = new MageInt(7);

        // When Myr Battlesphere enters the battlefield, put four 1/1 colorless Myr artifact creature tokens onto the battlefield.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new MyrToken(), 4), false));

        // Whenever Myr Battlesphere attacks, you may tap X untapped Myr you control. If you do, Myr Battlesphere gets +X/+0 until end of turn and deals X damage to defending player.
        this.addAbility(new AttacksTriggeredAbility(new MyrBattlesphereEffect(), true));
    }

    public MyrBattlesphere(final MyrBattlesphere card) {
        super(card);
    }

    @Override
    public MyrBattlesphere copy() {
        return new MyrBattlesphere(this);
    }

}

class MyrBattlesphereEffect extends OneShotEffect<MyrBattlesphereEffect> {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped Myr you control");

    static {
        filter.add(Predicates.not(new TappedPredicate()));
        filter.add(new SubtypePredicate("Myr"));
    }

    public MyrBattlesphereEffect() {
        super(Outcome.Damage);
        staticText = "tap X untapped Myr you control. If you do, {source} gets +X/+0 until end of turn and deals X damage to defending player";
    }

    public MyrBattlesphereEffect(final MyrBattlesphereEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int tappedAmount = 0;
            TargetPermanent target = new TargetPermanent(0,1,filter, false);
            while (true && controller.isInGame()) {
                target.clearChosen();
                if (target.canChoose(source.getControllerId(), game)) {
                    Map<String, Serializable> options = new HashMap<>();
                    options.put("UI.right.btn.text", "Myr tapping complete");
                    controller.choose(outcome, target, source.getControllerId(), game, options);
                    if (target.getTargets().size() > 0) {
                        UUID creature = target.getFirstTarget();
                        if (creature != null) {
                            game.getPermanent(creature).tap(game);
                            tappedAmount++;
                        }
                    } else {
                        break;
                    }
                }
                else {
                    break;
                }
            }
            if (tappedAmount > 0) {
                game.informPlayers(new StringBuilder(controller.getName()).append(" taps ").append(tappedAmount).append(" Myrs").toString());
                // boost effect
                game.addEffect(new BoostSourceEffect(tappedAmount, 0, Duration.EndOfTurn), source);
                // damage to defender
                UUID defenderId = game.getCombat().getDefenderId(source.getSourceId());
                Player defender = game.getPlayer(defenderId);
                if (defender != null) {
                    defender.damage(tappedAmount, source.getSourceId(), game, false, true);
                    return true;
                }

            }
            return true;
        }
        return false;
    }

    @Override
    public MyrBattlesphereEffect copy() {
        return new MyrBattlesphereEffect(this);
    }

}
