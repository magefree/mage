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
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SkipDrawStepEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author spjspj
 */
public class TaigamSidisisHand extends CardImpl {

    public TaigamSidisisHand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Skip your draw step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SkipDrawStepEffect()));

        // At the beginning of your upkeep, look at the top three cards of your library. Put one of them into your hand and the rest into your graveyard.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new TaigamSidisisHandDrawEffect(), TargetController.YOU, false));

        // {B}, {T}, Exile X cards from your graveyard: Target creature gets -X/-X until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TaigamSidisisHandEffect(), new ManaCostsImpl("{B}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(0, Integer.MAX_VALUE, new FilterCard("cards from your graveyard"))));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public TaigamSidisisHand(final TaigamSidisisHand card) {
        super(card);
    }

    @Override
    public TaigamSidisisHand copy() {
        return new TaigamSidisisHand(this);
    }
}

class TaigamSidisisHandEffect extends OneShotEffect {

    public TaigamSidisisHandEffect() {
        super(Outcome.Benefit);
        this.staticText = "creature gets -X/-X until end of turn";
    }

    public TaigamSidisisHandEffect(final TaigamSidisisHandEffect effect) {
        super(effect);
    }

    @Override
    public TaigamSidisisHandEffect copy() {
        return new TaigamSidisisHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));

            if (targetCreature != null) {
                int amount = 0;
                for (Cost cost : source.getCosts()) {
                    if (cost instanceof ExileFromGraveCost) {
                        amount = ((ExileFromGraveCost) cost).getExiledCards().size();
                        ContinuousEffect effect = new BoostTargetEffect(-amount, -amount, Duration.EndOfTurn);
                        effect.setTargetPointer(new FixedTarget(source.getTargets().getFirstTarget()));
                        game.addEffect(effect, source);
                    }
                }
            }
        }
        return false;
    }
}

class TaigamSidisisHandDrawEffect extends OneShotEffect {

    public TaigamSidisisHandDrawEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Look at the top three cards of your library. Put one of them into your hand and the rest into your graveyard";
    }

    public TaigamSidisisHandDrawEffect(final TaigamSidisisHandDrawEffect effect) {
        super(effect);
    }

    @Override
    public TaigamSidisisHandDrawEffect copy() {
        return new TaigamSidisisHandDrawEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());

        if (controller != null) {
            Cards cards = new CardsImpl();
            cards.addAll(controller.getLibrary().getTopCards(game, 3));
            if (!cards.isEmpty()) {
                controller.lookAtCards("Taigam, Sidisi's Hand", cards, game);
                TargetCard target = new TargetCard(Zone.LIBRARY, new FilterCard("card to put in your hand"));
                if (controller.choose(Outcome.Benefit, cards, target, game)) {
                    Card card = cards.get(target.getFirstTarget(), game);
                    if (card != null) {
                        controller.moveCards(card, Zone.HAND, source, game);
                        cards.remove(card);
                    }
                }
                controller.moveCards(cards, Zone.GRAVEYARD, source, game);
            }
            return true;
        }
        return false;
    }
}
