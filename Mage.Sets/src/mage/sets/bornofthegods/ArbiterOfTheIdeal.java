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
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.AddCardTypeTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.InspiredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class ArbiterOfTheIdeal extends CardImpl<ArbiterOfTheIdeal> {

    public ArbiterOfTheIdeal(UUID ownerId) {
        super(ownerId, 31, "Arbiter of the Ideal", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");
        this.expansionSetCode = "BNG";
        this.subtype.add("Sphinx");

        this.color.setBlue(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // <i>Inspired</i> - Whenever Arbiter of the Ideal becomes untapped, reveal the top card of your library. If it's an artifact, creature, or land card, you may put it onto the battlefield with a manifestation counter on it. It's an enchantment in addition to its other types.
        this.addAbility(new InspiredAbility(new ArbiterOfTheIdealEffect()));

    }

    public ArbiterOfTheIdeal(final ArbiterOfTheIdeal card) {
        super(card);
    }

    @Override
    public ArbiterOfTheIdeal copy() {
        return new ArbiterOfTheIdeal(this);
    }
}

class ArbiterOfTheIdealEffect extends OneShotEffect<ArbiterOfTheIdealEffect> {

    private static final FilterCard filter = new FilterCard();
    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.LAND)));
    }

    public ArbiterOfTheIdealEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "reveal the top card of your library. If it's an artifact, creature, or land card, you may put it onto the battlefield with a manifestation counter on it. It's an enchantment in addition to its other types";
    }

    public ArbiterOfTheIdealEffect(final ArbiterOfTheIdealEffect effect) {
        super(effect);
    }

    @Override
    public ArbiterOfTheIdealEffect copy() {
        return new ArbiterOfTheIdealEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        if (player.getLibrary().size() > 0) {
            Card card = player.getLibrary().getFromTop(game);
            Cards cards = new CardsImpl();
            cards.add(card);
            player.revealCards("Arbiter of the Ideal", cards, game);

            if (card != null) {
                if (filter.match(card, game) && player.chooseUse(outcome, new StringBuilder("Put ").append(card.getName()).append("onto battlefield?").toString(), game)) {
                    card.putOntoBattlefield(game, Zone.LIBRARY, source.getSourceId(), source.getControllerId());
                    Permanent permanent = game.getPermanent(card.getId());
                    if (permanent != null) {
                        permanent.addCounters(new ManifestationCounter(), game);
                        ContinuousEffect effect = new AddCardTypeTargetEffect(CardType.ENCHANTMENT, Duration.Custom);
                        effect.setTargetPointer(new FixedTarget(permanent.getId()));
                        game.addEffect(effect, source);
                    }
                }
            }
            return true;
        }

        return false;
    }
}

class ManifestationCounter extends Counter<ManifestationCounter> {

    public ManifestationCounter() {
        super("manifestation");
        this.count = 1;
    }
}
