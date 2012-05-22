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
package mage.sets.avacynrestored;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author noxx
 */
public class TyrantOfDiscord extends CardImpl<TyrantOfDiscord> {

    public TyrantOfDiscord(UUID ownerId) {
        super(ownerId, 162, "Tyrant of Discord", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{R}{R}{R}");
        this.expansionSetCode = "AVR";
        this.subtype.add("Elemental");

        this.color.setRed(true);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // When Tyrant of Discord enters the battlefield, target opponent chooses a permanent he or she controls at random and sacrifices it. If a nonland permanent is sacrificed this way, repeat this process.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TyrantOfDiscordEffect());
        ability.addTarget(new TargetOpponent(true));
        this.addAbility(ability);
    }

    public TyrantOfDiscord(final TyrantOfDiscord card) {
        super(card);
    }

    @Override
    public TyrantOfDiscord copy() {
        return new TyrantOfDiscord(this);
    }
}

class TyrantOfDiscordEffect extends OneShotEffect<TyrantOfDiscordEffect> {

    public TyrantOfDiscordEffect() {
        super(Constants.Outcome.Benefit);
        this.staticText = "target opponent chooses a permanent he or she controls at random and sacrifices it. If a nonland permanent is sacrificed this way, repeat this process";
    }

    public TyrantOfDiscordEffect(final TyrantOfDiscordEffect effect) {
        super(effect);
    }

    @Override
    public TyrantOfDiscordEffect copy() {
        return new TyrantOfDiscordEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID target = source.getFirstTarget();
        Player opponent = game.getPlayer(target);
        if (opponent != null) {
            boolean stop = false;
            while (!stop) {
                int count = game.getBattlefield().countAll(new FilterPermanent(), opponent.getId());
                if (count > 0) {
                    int random = (int)(Math.random()*count);
                    int index = 0;
                    for (Permanent permanent : game.getBattlefield().getAllActivePermanents(opponent.getId())) {
                        if (index == random) {
                            if (permanent.sacrifice(source.getSourceId(), game)) {
                                if (permanent.getCardType().contains(CardType.LAND)) {
                                    stop = true;
                                    game.informPlayers("Land permanent has been sacrificed: " + permanent.getName() + ". Stopping process.");
                                } else {
                                    game.informPlayers("Nonland permanent has been sacrificed: " + permanent.getName() + ". Repeating process.");
                                }
                            } else {
                                game.informPlayers("Couldn't sacrifice a permanent. Stopping the process.");
                                stop = true;
                            }
                            break;
                        }
                        index++;
                    }
                } else {
                    game.informPlayers("There is no permanent to sacrifice");
                    stop = true;
                }
            }
        }
        return true;
    }
}
