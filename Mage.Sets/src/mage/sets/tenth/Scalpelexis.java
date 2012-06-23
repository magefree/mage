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
package mage.sets.tenth;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.Card;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class Scalpelexis extends CardImpl<Scalpelexis> {

    public Scalpelexis(UUID ownerId) {
        super(ownerId, 105, "Scalpelexis", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{U}");
        this.expansionSetCode = "10E";
        this.subtype.add("Beast");

        this.color.setBlue(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Scalpelexis deals combat damage to a player, that player exiles the top four cards of his or her library. If two or more of those cards have the same name, repeat this process.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new ScalpelexisEffect(), false, true));
    }

    public Scalpelexis(final Scalpelexis card) {
        super(card);
    }

    @Override
    public Scalpelexis copy() {
        return new Scalpelexis(this);
    }
}

class ScalpelexisEffect extends OneShotEffect<ScalpelexisEffect> {

    public ScalpelexisEffect() {
        super(Constants.Outcome.Exile);
        this.staticText = "that player exiles the top four cards of his or her library. If two or more of those cards have the same name, repeat this process";
    }

    public ScalpelexisEffect(final ScalpelexisEffect effect) {
        super(effect);
    }

    @Override
    public ScalpelexisEffect copy() {
        return new ScalpelexisEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        List<String> namesFiltered = new ArrayList<String>();
        boolean doneOnce = false;

        while (checkDuplicatedNames(namesFiltered) || !doneOnce) {
            doneOnce = true;
            namesFiltered.clear();
            int count = Math.min(player.getLibrary().size(), 4);
            for (int i = 0; i < count; i++) {
                Card card = player.getLibrary().removeFromTop(game);
                if (card != null) {
                    namesFiltered.add(card.getName());
                    card.moveToExile(id, "Moved these cards to exile", source.getId(), game);
                }
            }
        }
        return true;
    }

    public boolean checkDuplicatedNames(List<String> string) {
    for (int i = 0; i < string.size()-1; i++) {
            String stringToCheck = string.get(i);
            if(stringToCheck == null) continue; //empty ignore
                for (int j = i+1; j < string.size(); j++) {
                    String stringToCompare = string.get(j);
                        if (stringToCheck.equals(stringToCompare)){
                            return true;
                        }
                }
    }
    return false;
    }
}