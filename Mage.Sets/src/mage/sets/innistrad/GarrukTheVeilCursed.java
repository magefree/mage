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
package mage.sets.innistrad;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continious.BoostControlledEffect;
import mage.abilities.effects.common.continious.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.WolfTokenWithDeathtouch;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author nantuko
 */
public class GarrukTheVeilCursed extends CardImpl<GarrukTheVeilCursed> {

    public GarrukTheVeilCursed(UUID ownerId) {
        super(ownerId, 1181, "Garruk, the Veil-Cursed", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "");
        this.expansionSetCode = "ISD";
        this.subtype.add("Garruk");

        // this card is the second face of double-faced card
        this.nightCard = true;
        this.canTransform = true;

        this.color.setGreen(true);
        this.color.setBlack(true);

        // +1 : Put a 1/1 black Wolf creature token with deathtouch onto the battlefield.
        LoyaltyAbility ability1 = new LoyaltyAbility(new CreateTokenEffect(new WolfTokenWithDeathtouch()), 1);
        this.addAbility(ability1);

        // -1 : Sacrifice a creature. If you do, search your library for a creature card, reveal it, put it into your hand, then shuffle your library.
        LoyaltyAbility ability2 = new LoyaltyAbility(new GarrukTheVeilCursedEffect(), -1);
        this.addAbility(ability2);

        // -3 : Creatures you control gain trample and get +X/+X until end of turn, where X is the number of creature cards in your graveyard.
        Effects effects1 = new Effects();
        BoostControlledEffect effect = new BoostControlledEffect(new GarrukTheVeilCursedValue(), new GarrukTheVeilCursedValue(), Constants.Duration.EndOfTurn);
        // +X/+X should be counted only once
        effect.setLockedIn(true);
        effect.setRule("Creatures you control get +X/+X until end of turn, where X is the number of creature cards in your graveyard");
        effects1.add(effect);
        effects1.add(new GainAbilityControlledEffect(TrampleAbility.getInstance(), Constants.Duration.EndOfTurn, new FilterCreaturePermanent()));
        this.addAbility(new LoyaltyAbility(effects1, -3));
    }

    public GarrukTheVeilCursed(final GarrukTheVeilCursed card) {
        super(card);
    }

    @Override
    public GarrukTheVeilCursed copy() {
        return new GarrukTheVeilCursed(this);
    }
}

class GarrukTheVeilCursedValue implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        Player player = game.getPlayer(sourceAbility.getControllerId());
        if (player != null) {
            return player.getGraveyard().getCards(new FilterCreatureCard(), game).size();
        }
        return 0;
    }

    @Override
    public DynamicValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "the number of creature cards in your graveyard";
    }

    @Override
    public String toString() {
        return "+X";
    }
}

class GarrukTheVeilCursedEffect extends OneShotEffect<GarrukTheVeilCursedEffect> {

    private static final FilterPermanent filterCreature = new FilterPermanent("a creature you control");

    static {
        filterCreature.add(new CardTypePredicate(CardType.CREATURE));
        filterCreature.add(new ControllerPredicate(Constants.TargetController.YOU));
    }

    public GarrukTheVeilCursedEffect() {
        super(Constants.Outcome.Benefit);
        staticText = "Sacrifice a creature. If you do, search your library for a creature card, reveal it, put it into your hand, then shuffle your library";
    }

    public GarrukTheVeilCursedEffect(final GarrukTheVeilCursedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());

        if (player == null) {
            return false;
        }

        // sacrifice a creature
        Target target = new TargetControlledPermanent(1, 1, filterCreature, false);
        boolean sacrificed = false;
        if (target.canChoose(player.getId(), game)) {
            while (!target.isChosen() && target.canChoose(player.getId(), game)) {
                player.choose(Constants.Outcome.Sacrifice, target, source.getSourceId(), game);
            }

            for (int idx = 0; idx < target.getTargets().size(); idx++) {
                Permanent permanent = game.getPermanent((UUID) target.getTargets().get(idx));
                if (permanent != null) {
                    sacrificed |= permanent.sacrifice(source.getSourceId(), game);
                }
            }
        }

        if (sacrificed) {
            // search
            FilterCreatureCard filter = new FilterCreatureCard();
            TargetCardInLibrary targetInLibrary = new TargetCardInLibrary(filter);
            Cards cards = new CardsImpl();
            if (player.searchLibrary(targetInLibrary, game)) {
                for (UUID cardId : targetInLibrary.getTargets()) {
                    Card card = player.getLibrary().remove(cardId, game);
                    if (card != null) {
                        card.moveToZone(Constants.Zone.HAND, source.getId(), game, false);
                        cards.add(card);
                    }
                }
            }
            // reveal
            if (cards.size() > 0) {
                player.revealCards("Garruk, the Veil-Cursed", cards, game);
            }
            // shuffle
            player.shuffleLibrary(game);
            return true;
        }
        return false;
    }

    @Override
    public GarrukTheVeilCursedEffect copy() {
        return new GarrukTheVeilCursedEffect(this);
    }
}

