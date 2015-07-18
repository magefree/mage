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
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author emerald000
 */
public class NissaSageAnimist extends CardImpl {

    public NissaSageAnimist(UUID ownerId) {
        super(ownerId, 189, "Nissa, Sage Animist", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "");
        this.expansionSetCode = "ORI";
        this.subtype.add("Nissa");
        this.color.setGreen(true);

        this.nightCard = true;
        this.canTransform = true;

        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(3)), false));

        // +1: Reveal the top card of your library. If it's a land card, put it onto the battlefield. Otherwise, put it into your hand.
        this.addAbility(new LoyaltyAbility(new NissaSageAnimistPlusOneEffect(), 1));

        // -2: Put a legendary 4/4 green Elemental creature token named Ashaya, the Awoken World onto the battlefield.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new NissaSageAnimistToken()), -2));

        // -7: Untap up to six target lands. They become 6/6 Elemental creatures. They're still lands.
        Ability ability = new LoyaltyAbility(new UntapTargetEffect(), -7);
        ability.addTarget(new TargetLandPermanent(0, 6, new FilterLandPermanent("lands"), false));
        ability.addEffect(new NissaSageAnimistMinusSevenEffect());
        this.addAbility(ability);
    }

    public NissaSageAnimist(final NissaSageAnimist card) {
        super(card);
    }

    @Override
    public NissaSageAnimist copy() {
        return new NissaSageAnimist(this);
    }
}

class NissaSageAnimistPlusOneEffect extends OneShotEffect {

    NissaSageAnimistPlusOneEffect() {
        super(Outcome.Benefit);
        this.staticText = "Reveal the top card of your library. If it's a land card, put it onto the battlefield. Otherwise, put it into your hand.";
    }

    NissaSageAnimistPlusOneEffect(final NissaSageAnimistPlusOneEffect effect) {
        super(effect);
    }

    @Override
    public NissaSageAnimistPlusOneEffect copy() {
        return new NissaSageAnimistPlusOneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && controller.getLibrary().size() > 0) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card == null) {
                return false;
            }
            CardsImpl cards = new CardsImpl();
            cards.add(card);
            controller.revealCards("Nissa, Sage Animist", cards, game);
            if (card.getCardType().contains(CardType.LAND)) {
                return controller.putOntoBattlefieldWithInfo(card, game, Zone.LIBRARY, source.getSourceId());
            } else {
                return controller.moveCards(card, Zone.LIBRARY, Zone.HAND, source, game);
            }
        }
        return true;
    }
}

class NissaSageAnimistToken extends Token {

    NissaSageAnimistToken() {
        super("Ashaya, the Awoken World", "legendary 4/4 green Elemental creature token named Ashaya, the Awoken World");
        this.setOriginalExpansionSetCode("ORI");
        this.getSupertype().add("Legendary");
        this.getPower().initValue(4);
        this.getToughness().initValue(4);
        this.color.setGreen(true);
        this.getSubtype().add("Elemental");
        this.getCardType().add(CardType.CREATURE);
    }
}

class NissaSageAnimistMinusSevenEffect extends ContinuousEffectImpl {

    NissaSageAnimistMinusSevenEffect() {
        super(Duration.EndOfGame, Outcome.BecomeCreature);
        this.staticText = "They become 6/6 Elemental creatures. They're still lands";
    }

    NissaSageAnimistMinusSevenEffect(final NissaSageAnimistMinusSevenEffect effect) {
        super(effect);
    }

    @Override
    public NissaSageAnimistMinusSevenEffect copy() {
        return new NissaSageAnimistMinusSevenEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (UUID permanentId : this.getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(permanentId);
            if (permanent != null) {
                switch (layer) {
                    case TypeChangingEffects_4:
                        if (!permanent.getCardType().contains(CardType.CREATURE)) {
                            permanent.getCardType().add(CardType.CREATURE);
                        }
                        if (!permanent.getSubtype().contains("Elemental")) {
                            permanent.getSubtype().add("Elemental");
                        }
                        break;
                    case PTChangingEffects_7:
                        if (sublayer == SubLayer.SetPT_7b) {
                            permanent.getToughness().setValue(6);
                            permanent.getPower().setValue(6);
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
