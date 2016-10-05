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
package mage.cards.g;

import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.Mana;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.mana.SimpleManaAbility;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author spjspj
 */
public class GaeasTouch extends CardImpl {

    public GaeasTouch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{G}{G}");

        // You may put a basic Forest card from your hand onto the battlefield. Activate this ability only any time you could cast a sorcery and only once each turn.
        LimitedTimesPerTurnActivatedAbility ability = new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new PutBasicForestOnBattlefieldEffect(), new GenericManaCost(0), 1);
        ability.setTiming(TimingRule.SORCERY);
        addAbility(ability);

        // Sacrifice Gaea's Touch: Add {G}{G} to your mana pool.
        addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.GreenMana(2), new SacrificeSourceCost()));
    }

    public GaeasTouch(final GaeasTouch card) {
        super(card);
    }

    @Override
    public GaeasTouch copy() {
        return new GaeasTouch(this);
    }
}

class PutBasicForestOnBattlefieldEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("basic Forest card");

    static {
        filter.add(Predicates.and(new CardTypePredicate(CardType.LAND), new SupertypePredicate("Basic")));
        filter.add(new SubtypePredicate("Forest"));
    }

    private static final String choiceText = "Put a basic Forest card from your hand onto the battlefield?";

    public PutBasicForestOnBattlefieldEffect() {
        super(Outcome.PutLandInPlay);
        this.staticText = "put a basic Forest card from your hand onto the battlefield";
    }

    public PutBasicForestOnBattlefieldEffect(final PutBasicForestOnBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public PutBasicForestOnBattlefieldEffect copy() {
        return new PutBasicForestOnBattlefieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || !player.chooseUse(Outcome.PutLandInPlay, choiceText, source, game)) {
            return false;
        }

        TargetCardInHand target = new TargetCardInHand(filter);
        if (player.choose(Outcome.PutLandInPlay, target, source.getSourceId(), game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                card.putOntoBattlefield(game, Zone.HAND, source.getSourceId(), source.getControllerId());
                return true;
            }
        }
        return false;
    }
}
