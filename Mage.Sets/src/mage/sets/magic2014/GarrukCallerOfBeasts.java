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
package mage.sets.magic2014;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.PutOntoBattlefieldTargetEffect;
import mage.abilities.effects.common.RevealLibraryPutIntoHandEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public class GarrukCallerOfBeasts extends CardImpl<GarrukCallerOfBeasts> {

    private static final FilterCreatureCard filterGreenCreature = new FilterCreatureCard("a green creature card from your hand");
    static {
        filterGreenCreature.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public GarrukCallerOfBeasts(UUID ownerId) {
        super(ownerId, 172, "Garruk, Caller of Beasts", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{4}{G}{G}");
        this.expansionSetCode = "M14";
        this.subtype.add("Garruk");

        this.color.setGreen(true);

        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(4)), false));

        // +1: Reveal the top 5 cards of your library. Put all creature cards revealed this way into your hand and the rest on the bottom of your library in any order.
        this.addAbility(new LoyaltyAbility(new RevealLibraryPutIntoHandEffect(5, new FilterCreatureCard("all creature cards"),true), 1));

        // -3: You may put a green creature card from your hand onto the battlefield.
        this.addAbility(new LoyaltyAbility(new GarrukCallerOfBeastsPutOntoBattlefieldEffect(), -3));

        // -7: You get an emblem with "Whenever you cast a creature spell, you may search your library for a creature card, put it onto the battlefield, then shuffle your library.");
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new GarrukCallerOfBeastsEmblem()), -7));

    }

    public GarrukCallerOfBeasts(final GarrukCallerOfBeasts card) {
        super(card);
    }

    @Override
    public GarrukCallerOfBeasts copy() {
        return new GarrukCallerOfBeasts(this);
    }
}
/**
 * Emblem: "Whenever you cast a creature spell, you may search your library for a creature card, put it onto the battlefield, then shuffle your library."
 */
class GarrukCallerOfBeastsEmblem extends Emblem {

    private static final FilterSpell filter = new FilterSpell("a creature spell");
    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    public GarrukCallerOfBeastsEmblem() {
        Effect effect = new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(new FilterCreatureCard("creature card")),false, true, Outcome.PutCreatureInPlay);
        Ability ability = new SpellCastControllerTriggeredAbility(Zone.COMMAND, effect, filter, true, false);
        this.getAbilities().add(ability);
    }
}

class GarrukCallerOfBeastsPutOntoBattlefieldEffect extends OneShotEffect<GarrukCallerOfBeastsPutOntoBattlefieldEffect> {

    private static final FilterCreatureCard filterGreenCreature = new FilterCreatureCard("a green creature card from your hand");

    static {
        filterGreenCreature.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public GarrukCallerOfBeastsPutOntoBattlefieldEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "You may put a green creature card from your hand onto the battlefield";
    }

    public GarrukCallerOfBeastsPutOntoBattlefieldEffect(final GarrukCallerOfBeastsPutOntoBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public GarrukCallerOfBeastsPutOntoBattlefieldEffect copy() {
        return new GarrukCallerOfBeastsPutOntoBattlefieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.getHand().count(filterGreenCreature, game) > 0) {

                if (controller.chooseUse(Outcome.PutCreatureInPlay,
                        "Put a green creature card onto the battlefield?", game)) {
                    Target target = new TargetCardInHand(filterGreenCreature);
                    target.setRequired(true);
                    if (controller.chooseTarget(outcome, target, source, game)) {
                        Card card = game.getCard(target.getFirstTarget());
                        if (card != null) {
                            controller.putOntoBattlefieldWithInfo(card, game, Zone.HAND, source.getSourceId());
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
