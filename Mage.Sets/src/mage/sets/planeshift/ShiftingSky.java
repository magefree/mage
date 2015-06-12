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
package mage.sets.planeshift;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author anonymous
 */
public class ShiftingSky extends CardImpl {

    public ShiftingSky(UUID ownerId) {
        super(ownerId, 32, "Shifting Sky", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");
        this.expansionSetCode = "PLS";

        // As Shifting Sky enters the battlefield, choose a color.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ChooseColorEffect(Outcome.Benefit));
        this.addAbility(ability);
        // All nonland permanents are the chosen color.
        Ability ability2 = new SimpleStaticAbility(Zone.BATTLEFIELD, new ShiftingSkyEffect());
        this.addAbility(ability2);
    }

    public ShiftingSky(final ShiftingSky card) {
        super(card);
    }

    @Override
    public ShiftingSky copy() {
        return new ShiftingSky(this);
    }
}

class ShiftingSkyEffect extends OneShotEffect {
    private static final FilterPermanent filter = new FilterPermanent("All nonland permanents");
    
    static {
        filter.add(
                Predicates.not(
                        new CardTypePredicate(CardType.LAND)
                )
        );
    }

    public ShiftingSkyEffect() {
        super(Outcome.Benefit);
        staticText = "All nonland permanents are the chosen color";
    }

    public ShiftingSkyEffect(final ShiftingSkyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());

        if (player != null && permanent != null) {
            ObjectColor color = (ObjectColor) game.getState().getValue(source.getSourceId() + "_color");
            
            if (color == null) {
                return false;
            }
            
            String colorString = color.toString();

            for (UUID playerId : player.getInRange()) {
                Player p = game.getPlayer(playerId);
                if (p != null) {
                    for (Permanent chosen : game.getBattlefield().getAllActivePermanents(filter, playerId, game)) {
                        setObject(chosen, colorString, game);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public ShiftingSkyEffect copy() {
        return new ShiftingSkyEffect(this);
    }

    private void setObject(Permanent chosen, String colorString, Game game) {
       switch (colorString) {
            case "W":
                chosen.getColor(game).setWhite(true);
                break;
            case "B":
                chosen.getColor(game).setBlack(true);
                break;
            case "U":
                chosen.getColor(game).setBlue(true);
                break;
            case "G":
                chosen.getColor(game).setGreen(true);
                break;
            case "R":
                chosen.getColor(game).setRed(true);
                break;
        }
    }
}
