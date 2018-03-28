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
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.NameACardEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes & L_J
 */
public class Nebuchadnezzar extends CardImpl {

    public Nebuchadnezzar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {X}, {T}: Choose a card name. Target opponent reveals X cards at random from their hand. Then that player discards all cards with that name revealed this way. Activate this ability only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, new NameACardEffect(NameACardEffect.TypeOfName.ALL), new ManaCostsImpl("{X}"), MyTurnCondition.instance);
        ability.addCost(new TapSourceCost());
        ability.addEffect(new NebuchadnezzarEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public Nebuchadnezzar(final Nebuchadnezzar card) {
        super(card);
    }

    @Override
    public Nebuchadnezzar copy() {
        return new Nebuchadnezzar(this);
    }
}

class NebuchadnezzarEffect extends OneShotEffect {

    public NebuchadnezzarEffect() {
        super(Outcome.Detriment);
        staticText = "Target opponent reveals X cards at random from their hand. Then that player discards all cards with that name revealed this way";
    }

    public NebuchadnezzarEffect(final NebuchadnezzarEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));
        MageObject sourceObject = game.getObject(source.getSourceId());
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + NameACardEffect.INFO_KEY);
        if (opponent != null && sourceObject != null && !cardName.isEmpty()) {
            int costX = source.getManaCostsToPay().getX();
            if (costX > 0 && !opponent.getHand().isEmpty()) {
                Cards cards = new CardsImpl();
                while (costX > 0) {
                    Card card = opponent.getHand().getRandom(game);
                    if (!cards.contains(card.getId())) {
                        cards.add(card);
                        costX--;
                    }
                    if (opponent.getHand().size() <= cards.size()) {
                        break;
                    }
                }
                opponent.revealCards(sourceObject.getIdName(), cards, game);
                for (Card cardToDiscard : cards.getCards(game)) {
                    if (cardToDiscard.getName().equals(cardName)) {
                        opponent.discard(cardToDiscard, source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public NebuchadnezzarEffect copy() {
        return new NebuchadnezzarEffect(this);
    }
}
