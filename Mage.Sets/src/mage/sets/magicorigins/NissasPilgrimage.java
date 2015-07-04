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
package mage.sets.magicorigins;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.common.SpellMasteryCondition;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterBasicLandCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public class NissasPilgrimage extends CardImpl {

    public NissasPilgrimage(UUID ownerId) {
        super(ownerId, 190, "Nissa's Pilgrimage", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{2}{G}");
        this.expansionSetCode = "ORI";

        // Search your library for up to two basic Forest cards, reveal those cards, and put one onto the battlefield tapped and the rest into your hand.  Then shuffle your library.
        // <i>Spell Mastery</i> — If there are two or more instant and/or sorcery cards in your graveyard, search your library for up to three basic Forest cards instead of two.
        this.getSpellAbility().addEffect(new NissasPilgrimageEffect());
    }

    public NissasPilgrimage(final NissasPilgrimage card) {
        super(card);
    }

    @Override
    public NissasPilgrimage copy() {
        return new NissasPilgrimage(this);
    }
}

class NissasPilgrimageEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterBasicLandCard("basic Forest");

    static {
        filter.add(new SubtypePredicate("Forest"));
    }

    public NissasPilgrimageEffect() {
        super(Outcome.Benefit);
        this.staticText = "Search your library for up to two basic Forest cards, reveal those cards, and put one onto the battlefield tapped and the rest into your hand.  Then shuffle your library."
                + "<br><i>Spell Mastery</i> — If there are two or more instant and/or sorcery cards in your graveyard, search your library for up to three basic Forest cards instead of two.";
    }

    public NissasPilgrimageEffect(final NissasPilgrimageEffect effect) {
        super(effect);
    }

    @Override
    public NissasPilgrimageEffect copy() {
        return new NissasPilgrimageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            int number = 2;
            if (SpellMasteryCondition.getInstance().apply(game, source)) {
                number++;
            }
            TargetCardInLibrary target = new TargetCardInLibrary(0, number, filter);
            controller.chooseTarget(outcome, target, source, game);
            if (!target.getTargets().isEmpty()) {
                Cards cards = new CardsImpl(target.getTargets());
                controller.revealCards(sourceObject.getIdName(), cards, game);
                if (!cards.isEmpty()) {
                    Card card = cards.getRandom(game);
                    if (card != null) {
                        cards.remove(card);
                        controller.putOntoBattlefieldWithInfo(card, game, Zone.LIBRARY, source.getSourceId(), true);
                        controller.moveCards(cards, Zone.LIBRARY, Zone.HAND, source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
