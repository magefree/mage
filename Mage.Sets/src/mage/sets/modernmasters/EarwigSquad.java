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
package mage.sets.modernmasters;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.ProwlCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ProwlAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class EarwigSquad extends CardImpl<EarwigSquad> {

    public EarwigSquad(UUID ownerId) {
        super(ownerId, 82, "Earwig Squad", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.expansionSetCode = "MMA";
        this.subtype.add("Goblin");
        this.subtype.add("Rogue");

        this.color.setBlack(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Prowl {2}{B}
        this.addAbility(new ProwlAbility(this, "{2}{B}"));
        // When Earwig Squad enters the battlefield, if its prowl cost was paid, search target opponent's library for three cards and exile them. Then that player shuffles his or her library.
        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new EarwigSquadEffect(), false);
        ability.addTarget(new TargetOpponent(true));
        this.addAbility(new ConditionalTriggeredAbility(ability, ProwlCondition.getInstance(),
                "When {this} enters the battlefield, if its prowl cost was paid, search target opponent's library for three cards and exile them. Then that player shuffles his or her library."));

    }

    public EarwigSquad(final EarwigSquad card) {
        super(card);
    }

    @Override
    public EarwigSquad copy() {
        return new EarwigSquad(this);
    }
}

class EarwigSquadEffect extends OneShotEffect {

    public EarwigSquadEffect() {
        super(Outcome.Benefit);
        staticText = "search target opponent's library for three cards and exile them. Then that player shuffles his or her library";
    }

    public EarwigSquadEffect(final EarwigSquadEffect effect) {
        super(effect);
    }

    @Override
    public EarwigSquadEffect copy() {
        return new EarwigSquadEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(source.getFirstTarget());
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && opponent != null) {
            TargetCardInLibrary target = new TargetCardInLibrary(0, 3, new FilterCard("cards from opponents library to exile"));            
            if (player.searchLibrary(target, game, opponent.getId())) {
                List<UUID> targets = target.getTargets();
                for (UUID targetId : targets) {
                    Card card = opponent.getLibrary().remove(targetId, game);
                    if (card != null) {
                        player.moveCardToExileWithInfo(card, null, null, source.getSourceId(), game, Zone.LIBRARY);
                    }
                }
            }
            opponent.shuffleLibrary(game);
            return true;
        }
        return false;
    }
}
