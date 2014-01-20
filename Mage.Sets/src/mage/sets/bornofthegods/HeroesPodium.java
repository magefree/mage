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
package mage.sets.bornofthegods;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BoostControlledEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author LevelX2
 */
public class HeroesPodium extends CardImpl<HeroesPodium> {
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Each legendary creature you control");
    static {
        filter.add(new SupertypePredicate("Legendary"));
    }
    public HeroesPodium(UUID ownerId) {
        super(ownerId, 159, "Heroes' Podium", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{5}");
        this.expansionSetCode = "BNG";
        this.supertype.add("Legendary");

        // Each legendary creature you control gets +1/+1 for each other legendary creature you control.
        DynamicValue xValue = new HeroesPodiumLegendaryCount();
        Effect effect = new BoostControlledEffect(xValue, xValue, Duration.WhileOnBattlefield, filter, false);
        effect.setText("Each legendary creature you control gets +1/+1 for each other legendary creature you control");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
        // {X}, {T}: Look at the top X cards of your library. You may reveal a legendary creature card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new HeroesPodiumEffect(), new ManaCostsImpl("{X}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

    }

    public HeroesPodium(final HeroesPodium card) {
        super(card);
    }

    @Override
    public HeroesPodium copy() {
        return new HeroesPodium(this);
    }
}

class HeroesPodiumLegendaryCount implements DynamicValue {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("other legendary creature you control");
    static {
        filter.add(new SupertypePredicate("Legendary"));
    }

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        int value = game.getBattlefield().count(filter, sourceAbility.getSourceId(), sourceAbility.getControllerId(), game);
        if (value > 0) {
            value--;
        }
        return value;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return filter.getMessage();
    }

    @Override
    public HeroesPodiumLegendaryCount copy() {
        return new HeroesPodiumLegendaryCount();
    }
}

class HeroesPodiumEffect extends OneShotEffect<HeroesPodiumEffect> {

    private static final FilterCreatureCard filter = new FilterCreatureCard("a legendary creature card");
    static {
        filter.add(new SupertypePredicate(("Legendary")));
    }

    public HeroesPodiumEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Look at the top X cards of your library. You may reveal a legendary creature card from among them and put it into your hand. Put the rest on the bottom of your library in a random order";
    }

    public HeroesPodiumEffect(final HeroesPodiumEffect effect) {
        super(effect);
    }

    @Override
    public HeroesPodiumEffect copy() {
        return new HeroesPodiumEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        Cards cards = new CardsImpl(Zone.PICK);
        int count = source.getManaCostsToPay().getX();
        count = Math.min(player.getLibrary().size(), count);
        boolean legendaryIncluded = false;
        for (int i = 0; i < count; i++) {
            Card card = player.getLibrary().removeFromTop(game);
            if (card != null) {
                cards.add(card);
                if (filter.match(card, game)) {
                    legendaryIncluded = true;
                }
                game.setZone(card.getId(), Zone.PICK);
            }
        }
        player.lookAtCards("Heroes' Podium", cards, game);

        // You may reveal a legendary creature card from among them and put it into your hand.
        if (!cards.isEmpty() && legendaryIncluded && player.chooseUse(outcome, "Put a legendary creature card into your hand?", game)) {
            if (cards.size() == 1) {
                Card card = cards.getRandom(game);
                cards.remove(card);
                card.moveToZone(Zone.HAND, source.getSourceId(), game, false);
                return true;
            } else {
                TargetCard target = new TargetCard(Zone.PICK, filter);
                if (player.choose(outcome, cards, target, game)) {
                    Card card = cards.get(target.getFirstTarget(), game);
                    if (card != null) {
                        cards.remove(card);
                        card.moveToZone(Zone.HAND, source.getSourceId(), game, false);
                    }
                }
            }
        }

        // Put the rest on the bottom of your library in a random order
        while (cards.size() > 0) {
            Card card = cards.getRandom(game);
            if (card != null) {
                cards.remove(card);
                card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, false);
            }
        }
        return true;
    }
}
