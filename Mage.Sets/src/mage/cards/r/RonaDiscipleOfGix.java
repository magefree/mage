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
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterHistoricCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class RonaDiscipleOfGix extends CardImpl {

    public RonaDiscipleOfGix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Rona, Disciple of Gix enters the battlefield, you may exile target historic card from your graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileTargetEffect().setToSourceExileZone(true), true);
        ability.addTarget(new TargetCardInYourGraveyard(new FilterHistoricCard()));
        this.addAbility(ability);

        // You may cast nonland cards exiled with Rona.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new RonaDiscipleOfGixPlayNonLandEffect()));

        // {4}, {T}: Exile the top card of your library.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RonaDiscipleOfGixExileEffect(), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public RonaDiscipleOfGix(final RonaDiscipleOfGix card) {
        super(card);
    }

    @Override
    public RonaDiscipleOfGix copy() {
        return new RonaDiscipleOfGix(this);
    }
}

class RonaDiscipleOfGixPlayNonLandEffect extends AsThoughEffectImpl {

    public RonaDiscipleOfGixPlayNonLandEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "You may cast nonland cards exiled with {this}";
    }

    public RonaDiscipleOfGixPlayNonLandEffect(final RonaDiscipleOfGixPlayNonLandEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public RonaDiscipleOfGixPlayNonLandEffect copy() {
        return new RonaDiscipleOfGixPlayNonLandEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (affectedControllerId.equals(source.getControllerId())) {
            Card card = game.getCard(objectId);
            MageObject sourceObject = source.getSourceObject(game);
            if (card != null && !card.isLand() && sourceObject != null) {
                UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
                if (exileId != null) {
                    ExileZone exileZone = game.getState().getExile().getExileZone(exileId);
                    return exileZone != null && exileZone.contains(objectId);
                }
            }
        }
        return false;
    }
}

class RonaDiscipleOfGixExileEffect extends OneShotEffect {

    public RonaDiscipleOfGixExileEffect() {
        super(Outcome.Exile);
        this.staticText = "Exile the top card of your library";
    }

    public RonaDiscipleOfGixExileEffect(final RonaDiscipleOfGixExileEffect effect) {
        super(effect);
    }

    @Override
    public RonaDiscipleOfGixExileEffect copy() {
        return new RonaDiscipleOfGixExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
                controller.moveCardsToExile(card, source, game, true, exileId, sourceObject.getIdName());
            }
            return true;
        }
        return false;
    }
}
