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
package mage.sets.shardsofalara;

import mage.Constants.*;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayVariableLoyaltyCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.Filter.ComparisonType;
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCardInLibrary;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author North
 */
public class TezzeretTheSeeker extends CardImpl<TezzeretTheSeeker> {

    public TezzeretTheSeeker(UUID ownerId) {
        super(ownerId, 60, "Tezzeret the Seeker", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{3}{U}{U}");
        this.expansionSetCode = "ALA";
        this.subtype.add("Tezzeret");

        this.color.setBlue(true);
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(4)), false));

        // +1: Untap up to two target artifacts.
        LoyaltyAbility ability = new LoyaltyAbility(new UntapTargetEffect(), 1);
        ability.addTarget(new TargetArtifactPermanent(0, 2));
        this.addAbility(ability);
        // -X: Search your library for an artifact card with converted mana cost X or less and put it onto the battlefield. Then shuffle your library.
        this.addAbility(new LoyaltyAbility(new TezzeretTheSeekerEffect2()));
        // -5: Artifacts you control become 5/5 artifact creatures until end of turn.
        this.addAbility(new LoyaltyAbility(new TezzeretTheSeekerEffect3(), -5));
    }

    public TezzeretTheSeeker(final TezzeretTheSeeker card) {
        super(card);
    }

    @Override
    public TezzeretTheSeeker copy() {
        return new TezzeretTheSeeker(this);
    }
}

class TezzeretTheSeekerEffect2 extends OneShotEffect<TezzeretTheSeekerEffect2> {

    public TezzeretTheSeekerEffect2() {
        super(Outcome.DrawCard);
        this.staticText = "Search your library for an artifact card with converted mana cost X or less and put it onto the battlefield. Then shuffle your library";
    }

    public TezzeretTheSeekerEffect2(final TezzeretTheSeekerEffect2 effect) {
        super(effect);
    }

    @Override
    public TezzeretTheSeekerEffect2 copy() {
        return new TezzeretTheSeekerEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        int cmc = 0;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof PayVariableLoyaltyCost) {
                cmc = ((PayVariableLoyaltyCost) cost).getAmount();
            }
        }

        FilterArtifactCard filter = new FilterArtifactCard("artifact card with converted mana cost " + cmc);
        filter.add(new ConvertedManaCostPredicate(ComparisonType.Equal, cmc));
        TargetCardInLibrary target = new TargetCardInLibrary(filter);

        if (player.searchLibrary(target, game)) {
            Card card = player.getLibrary().getCard(target.getFirstTarget(), game);
            if (card != null) {
                card.putOntoBattlefield(game, Zone.HAND, source.getId(), source.getControllerId());
            }
            player.shuffleLibrary(game);
            return true;
        }
        player.shuffleLibrary(game);
        return false;
    }
}

class TezzeretTheSeekerEffect3 extends ContinuousEffectImpl<TezzeretTheSeekerEffect3> {

    public TezzeretTheSeekerEffect3() {
        super(Duration.EndOfTurn, Outcome.BecomeCreature);
        this.staticText = "Artifacts you control become 5/5 artifact creatures until end of turn";
    }

    public TezzeretTheSeekerEffect3(final TezzeretTheSeekerEffect3 effect) {
        super(effect);
    }

    @Override
    public TezzeretTheSeekerEffect3 copy() {
        return new TezzeretTheSeekerEffect3(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        List<Permanent> permanents = game.getBattlefield().getAllActivePermanents(new FilterArtifactPermanent(), source.getControllerId(), game);
        for (Permanent permanent : permanents) {
            if (permanent != null) {
                switch (layer) {
                    case TypeChangingEffects_4:
                        if (sublayer == SubLayer.NA) {
                            if (!permanent.getCardType().contains(CardType.ARTIFACT)) {
                                permanent.getCardType().add(CardType.ARTIFACT);
                            }
                            if (!permanent.getCardType().contains(CardType.CREATURE)) {
                                permanent.getCardType().add(CardType.CREATURE);
                            }
                            permanent.getSubtype().clear();
                        }
                        break;
                    case PTChangingEffects_7:
                        if (sublayer == SubLayer.SetPT_7b) {
                            permanent.getPower().setValue(5);
                            permanent.getToughness().setValue(5);
                        }
                }
            }
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4 || layer == Layer.PTChangingEffects_7;
    }
}
