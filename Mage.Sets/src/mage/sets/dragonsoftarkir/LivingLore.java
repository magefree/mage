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
package mage.sets.dragonsoftarkir;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class LivingLore extends CardImpl {

    public LivingLore(UUID ownerId) {
        super(ownerId, 61, "Living Lore", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.expansionSetCode = "DTK";
        this.subtype.add("Avatar");
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // As Living Lore enters the battlefield, exile an instant or sorcery card from your graveyard.
        this.addAbility(new EntersBattlefieldAbility(new LivingLoreExileEffect(), "exile an instant or sorcery card from your graveyard"));

        // Living Lore's power and toughness are each equal to the exiled card's converted mana cost.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LivingLoreSetPowerToughnessSourceEffect()));

        // Whenever Living Lore deals combat damage, you may sacrifice it. If you do, you may cast the exiled card without paying its mana cost.
        this.addAbility(new DealsCombatDamageTriggeredAbility(new LivingLoreSacrificeEffect(), true));
    }

    public LivingLore(final LivingLore card) {
        super(card);
    }

    @Override
    public LivingLore copy() {
        return new LivingLore(this);
    }
}

class LivingLoreExileEffect extends OneShotEffect {

    public LivingLoreExileEffect() {
        super(Outcome.Exile);
        staticText = "exile an instant or sorcery card from your graveyard";
    }

    public LivingLoreExileEffect(final LivingLoreExileEffect effect) {
        super(effect);
    }

    @Override
    public LivingLoreExileEffect copy() {
        return new LivingLoreExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject != null && controller != null){
            TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(new FilterInstantOrSorceryCard("instant or sorcery card from your graveyard"));
            if (controller.chooseTarget(outcome, target, source, game)) {
                UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
                Card card = controller.getGraveyard().get(target.getFirstTarget(), game);
                if (card != null) {
                    controller.moveCardToExileWithInfo(card, exileId, sourceObject.getName(), source.getSourceId(), game, Zone.GRAVEYARD, true);
                }
            }
            return true;
        }
        return false;
    }

}

class LivingLoreSetPowerToughnessSourceEffect extends ContinuousEffectImpl {

    public LivingLoreSetPowerToughnessSourceEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.SetPT_7b, Outcome.BoostCreature);
        staticText = "{this}'s power and toughness are each equal to the exiled card's converted mana cost";
    }

    public LivingLoreSetPowerToughnessSourceEffect(final LivingLoreSetPowerToughnessSourceEffect effect) {
        super(effect);
    }

    @Override
    public LivingLoreSetPowerToughnessSourceEffect copy() {
        return new LivingLoreSetPowerToughnessSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject mageObject = source.getSourceObject(game);
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && mageObject == null && new MageObjectReference(permanent, game).refersTo(mageObject, game)) {
            discard();
            return false;
        }
        UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
        if (exileId != null) {
            ExileZone exileZone = game.getExile().getExileZone(exileId);
            if (exileZone == null) {
                return false;
            }
            Card exiledCard = null;
            for (Card card :exileZone.getCards(game)) {
                exiledCard = card;
                break;
            }
            if (exiledCard != null) {
                int value = exiledCard.getManaCost().convertedManaCost();
                permanent.getPower().setValue(value);
                permanent.getToughness().setValue(value);
            }
        }
        return true;
    }
}

class LivingLoreSacrificeEffect extends OneShotEffect {

    public LivingLoreSacrificeEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may sacrifice it. If you do, you may cast the exiled card without paying its mana cost";
    }

    public LivingLoreSacrificeEffect(final LivingLoreSacrificeEffect effect) {
        super(effect);
    }

    @Override
    public LivingLoreSacrificeEffect copy() {
        return new LivingLoreSacrificeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            MageObject mageObject = source.getSourceObject(game);
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null && mageObject != null && new MageObjectReference(permanent, game).refersTo(mageObject, game)) {
                if (permanent.sacrifice(source.getSourceId(), game)) {
                    UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
                    if (exileId != null) {
                        ExileZone exileZone = game.getExile().getExileZone(exileId);
                        Card exiledCard = null;
                        if (exileZone != null) {
                            for (Card card :exileZone.getCards(game)) {
                                exiledCard = card;
                                break;
                            }
                        }
                        if (exiledCard != null) {
                            if (exiledCard.getSpellAbility().canChooseTarget(game)) {
                                controller.cast(exiledCard.getSpellAbility(), game, true);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
