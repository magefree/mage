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
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ProwessAbility;
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
public class AbbotOfKeralKeep extends CardImpl {

    public AbbotOfKeralKeep(UUID ownerId) {
        super(ownerId, 127, "Abbot of Keral Keep", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.expansionSetCode = "ORI";
        this.subtype.add("Human");
        this.subtype.add("Monk");
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Prowess
        this.addAbility(new ProwessAbility());
        
        // When Abbot of Keral Keep enters the battlefield, exile the top card of your library. Until end of turn, you may play that card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AbbotOfKeralKeepExileEffect()));
    }

    public AbbotOfKeralKeep(final AbbotOfKeralKeep card) {
        super(card);
    }

    @Override
    public AbbotOfKeralKeep copy() {
        return new AbbotOfKeralKeep(this);
    }
}

class AbbotOfKeralKeepExileEffect extends OneShotEffect {

    public AbbotOfKeralKeepExileEffect() {
        super(Outcome.Detriment);
        this.staticText = "exile the top card of your library. Until end of turn, you may play that card";
    }

    public AbbotOfKeralKeepExileEffect(final AbbotOfKeralKeepExileEffect effect) {
        super(effect);
    }

    @Override
    public AbbotOfKeralKeepExileEffect copy() {
        return new AbbotOfKeralKeepExileEffect(this);
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
                ContinuousEffect effect = new AbbotOfKeralKeepCastFromExileEffect();
                effect.setTargetPointer(new FixedTarget(card.getId()));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }
}

class AbbotOfKeralKeepCastFromExileEffect extends AsThoughEffectImpl {

    public AbbotOfKeralKeepCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "You may play the card from exile";
    }

    public AbbotOfKeralKeepCastFromExileEffect(final AbbotOfKeralKeepCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public AbbotOfKeralKeepCastFromExileEffect copy() {
        return new AbbotOfKeralKeepCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return source.getControllerId().equals(affectedControllerId) &&
                objectId.equals(getTargetPointer().getFirst(game, source));
    }
}
