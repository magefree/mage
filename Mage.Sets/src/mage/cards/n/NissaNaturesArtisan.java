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

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public class NissaNaturesArtisan extends CardImpl {

    public NissaNaturesArtisan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.PLANESWALKER},"{4}{G}{G}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Nissa");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(5));

        // +3: You gain 3 life.
        this.addAbility(new LoyaltyAbility(new GainLifeEffect(3), 3));

        // -4: Reveal the top two cards of your library. Put all land cards from among them onto the battlefield and the rest into your hand.
        this.addAbility(new LoyaltyAbility(new NissaNaturesArtisanEffect(), -4));

        // -12: Creatures you control get +5/+5 and gain trample until end of turn.
        Effect effect = new BoostControlledEffect(5, 5, Duration.EndOfTurn);
        effect.setText("Creature you control get +5/+5");
        LoyaltyAbility ability = new LoyaltyAbility(effect, -12);
        ability.addEffect(new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.EndOfTurn));
        this.addAbility(ability);
    }

    public NissaNaturesArtisan(final NissaNaturesArtisan card) {
        super(card);
    }

    @Override
    public NissaNaturesArtisan copy() {
        return new NissaNaturesArtisan(this);
    }
}

class NissaNaturesArtisanEffect extends OneShotEffect {

    public NissaNaturesArtisanEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "Reveal the top two cards of your library. Put all land cards from among them onto the battlefield and the rest into your hand";
    }

    public NissaNaturesArtisanEffect(final NissaNaturesArtisanEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller == null || sourceObject == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        cards.addAll(controller.getLibrary().getTopCards(game, 2));
        if (!cards.isEmpty()) {
            controller.revealCards(sourceObject.getIdName(), cards, game);
            Set<Card> toBattlefield = new LinkedHashSet<>();
            for (Card card : cards.getCards(new FilterLandCard(), source.getSourceId(), source.getControllerId(), game)) {
                cards.remove(card);
                toBattlefield.add(card);
            }
            controller.moveCards(toBattlefield, Zone.BATTLEFIELD, source, game, false, false, true, null);
            controller.moveCards(cards, Zone.HAND, source, game);
        }
        return true;
    }

    @Override
    public NissaNaturesArtisanEffect copy() {
        return new NissaNaturesArtisanEffect(this);
    }

}
