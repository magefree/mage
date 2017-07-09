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
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.ManaType;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.ManaPoolItem;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class VizierOfTheMenagerie extends CardImpl {

    public VizierOfTheMenagerie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add("Naga");
        this.subtype.add("Cleric");
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // You may look at the top card of your library. (You may do this at any time.)
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new VizierOfTheMenagerieTopCardRevealedEffect()));

        // You may cast the top card of your library if it's a creature card.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new VizierOfTheMenagerieTopCardCastEffect()));

        // You may spend mana as though it were mana of any type to cast creature spells.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new VizierOfTheMenagerieManaEffect()));

    }

    public VizierOfTheMenagerie(final VizierOfTheMenagerie card) {
        super(card);
    }

    @Override
    public VizierOfTheMenagerie copy() {
        return new VizierOfTheMenagerie(this);
    }
}

class VizierOfTheMenagerieTopCardRevealedEffect extends ContinuousEffectImpl {

    public VizierOfTheMenagerieTopCardRevealedEffect() {
        super(Duration.WhileOnBattlefield, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "You may look at the top card of your library. (You may do this at any time.)";
    }

    public VizierOfTheMenagerieTopCardRevealedEffect(final VizierOfTheMenagerieTopCardRevealedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card topCard = controller.getLibrary().getFromTop(game);
            if (topCard != null) {
                MageObject vizierOfTheMenagerie = source.getSourceObject(game);
                if (vizierOfTheMenagerie != null) {
                    controller.lookAtCards("Top card of " + vizierOfTheMenagerie.getIdName() + " controller's library", topCard, game);
                }
            }
        }
        return true;
    }

    @Override
    public VizierOfTheMenagerieTopCardRevealedEffect copy() {
        return new VizierOfTheMenagerieTopCardRevealedEffect(this);
    }
}

class VizierOfTheMenagerieTopCardCastEffect extends AsThoughEffectImpl {

    public VizierOfTheMenagerieTopCardCastEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "You may cast the top card of your library if it's a creature card";
    }

    public VizierOfTheMenagerieTopCardCastEffect(final VizierOfTheMenagerieTopCardCastEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public VizierOfTheMenagerieTopCardCastEffect copy() {
        return new VizierOfTheMenagerieTopCardCastEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (affectedControllerId.equals(source.getControllerId())) {
            Card card = game.getCard(objectId);
            if (card != null) {
                Player controller = game.getPlayer(affectedControllerId);
                if (controller != null) {
                    Card topCard = controller.getLibrary().getFromTop(game);
                    MageObject vizierOfTheMenagerie = game.getObject(source.getSourceId());
                    if (vizierOfTheMenagerie != null
                            && topCard != null) {
                        if (topCard == card
                                && topCard.isCreature()
                                && topCard.getSpellAbility() != null
                                && topCard.getSpellAbility().spellCanBeActivatedRegularlyNow(controller.getId(), game)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}

class VizierOfTheMenagerieManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    public VizierOfTheMenagerieManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "You may spend mana as though it were mana of any type to cast creature spells";
    }

    public VizierOfTheMenagerieManaEffect(final VizierOfTheMenagerieManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public VizierOfTheMenagerieManaEffect copy() {
        return new VizierOfTheMenagerieManaEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (source.getControllerId().equals(affectedControllerId)) {
            MageObject mageObject = game.getObject(objectId);
            return mageObject != null
                    && mageObject.isCreature();
        }
        return false;
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }
}
