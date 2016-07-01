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
package mage.sets.eldritchmoon;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Library;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fireshoes
 */
public class StromkirkMystic extends CardImpl {

    public StromkirkMystic(UUID ownerId) {
        super(ownerId, 146, "Stromkirk Mystic", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.expansionSetCode = "EMN";
        this.subtype.add("Vampire");
        this.subtype.add("Horror");
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Stromkirk Mystic deals combat damage to a player, exile the top card of your library. Until end of turn, you may cast that card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new StromkirkMysticExileEffect(), false));

        // Madness {1}{R}
        this.addAbility(new MadnessAbility(this, new ManaCostsImpl("{1}{R}")));
    }

    public StromkirkMystic(final StromkirkMystic card) {
        super(card);
    }

    @Override
    public StromkirkMystic copy() {
        return new StromkirkMystic(this);
    }
}

class StromkirkMysticExileEffect extends OneShotEffect {

    public StromkirkMysticExileEffect() {
        super(Outcome.Detriment);
        this.staticText = "Exile the top card of your library. Until end of turn, you may cast that card";
    }

    public StromkirkMysticExileEffect(final StromkirkMysticExileEffect effect) {
        super(effect);
    }

    @Override
    public StromkirkMysticExileEffect copy() {
        return new StromkirkMysticExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourcePermanent != null && controller != null && controller.getLibrary().size() > 0) {
            Library library = controller.getLibrary();
            Card card = library.removeFromTop(game);
            if (card != null) {
                String exileName = new StringBuilder(sourcePermanent.getIdName()).append(" <this card may be played the turn it was exiled>").toString();
                controller.moveCardToExileWithInfo(card, source.getSourceId(), exileName, source.getSourceId(), game, Zone.LIBRARY, true);
                ContinuousEffect effect = new StromkirkMysticCastFromExileEffect();
                effect.setTargetPointer(new FixedTarget(card.getId()));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }
}

class StromkirkMysticCastFromExileEffect extends AsThoughEffectImpl {

    public StromkirkMysticCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "You may play the card from exile";
    }

    public StromkirkMysticCastFromExileEffect(final StromkirkMysticCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public StromkirkMysticCastFromExileEffect copy() {
        return new StromkirkMysticCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return source.getControllerId().equals(affectedControllerId) &&
                objectId.equals(getTargetPointer().getFirst(game, source));
    }
}
