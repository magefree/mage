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
package mage.cards.h;

import java.util.Objects;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInExile;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class HellcarverDemon extends CardImpl {

    public HellcarverDemon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}{B}");
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        this.addAbility(FlyingAbility.getInstance());

        // Whenever Hellcarver Demon deals combat damage to a player, sacrifice all other permanents you control and discard your hand. Exile the top six cards of your library. You may cast any number of nonland cards exiled this way without paying their mana costs.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new HellcarverDemonEffect(), false));
    }

    public HellcarverDemon(final HellcarverDemon card) {
        super(card);
    }

    @Override
    public HellcarverDemon copy() {
        return new HellcarverDemon(this);
    }
}

class HellcarverDemonEffect extends OneShotEffect {

    private static final FilterControlledPermanent filterPermanents = new FilterControlledPermanent("Permanent");
    private static FilterNonlandCard filter = new FilterNonlandCard("nonland card exiled with Hellcarver Demon");

    public HellcarverDemonEffect() {
        super(Outcome.PlayForFree);
        staticText = "sacrifice all other permanents you control and discard your hand. Exile the top six cards of your library. You may cast any number of nonland cards exiled this way without paying their mana costs.";
    }

    public HellcarverDemonEffect(final HellcarverDemonEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent hellcarverDemon = game.getPermanent(source.getSourceId());

        for (Permanent permanent : game.getBattlefield().getActivePermanents(filterPermanents, source.getControllerId(), game)) {
            if (!Objects.equals(permanent, hellcarverDemon)) {
                permanent.sacrifice(source.getSourceId(), game);
            }
        }

        if (controller != null && !controller.getHand().isEmpty()) {
            int cardsInHand = controller.getHand().size();
            controller.discard(cardsInHand, false, source, game);
        }

        for (int i = 0; i < 6; i++) {
            if (controller != null
                    && controller.getLibrary().hasCards()) {
                Card topCard = controller.getLibrary().getFromTop(game);
                topCard.moveToExile(source.getSourceId(), "Cards exiled by Hellcarver Demon", source.getSourceId(), game);
            }
        }

        while (controller != null
                && controller.canRespond()
                && controller.chooseUse(Outcome.PlayForFree, controller.getLogName() + " can cast another nonland card exiled with Hellcarver Demon without paying that card's mana cost.", source, game)) {
            TargetCardInExile target = new TargetCardInExile(filter, source.getSourceId());
            while (controller.chooseUse(Outcome.PlayForFree, "Cast another spell exiled by Hellcarver Demon?", source, game)) {
                controller.choose(Outcome.PlayForFree, game.getExile().getExileZone(source.getSourceId()), target, game);
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    ContinuousEffect effect = new HellcarverDemonCastFromExileEffect();
                    effect.setTargetPointer(new FixedTarget(card.getId()));
                    game.addEffect(effect, source);
                    controller.cast(card.getSpellAbility(), game, true);
                }
                target.clearChosen();
            }
            return true;
        }
        return false;
    }

    @Override
    public HellcarverDemonEffect copy() {
        return new HellcarverDemonEffect(this);
    }
}

class HellcarverDemonCastFromExileEffect extends AsThoughEffectImpl {

    public HellcarverDemonCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        staticText = "You may play the card from exile without paying its mana cost";
    }

    public HellcarverDemonCastFromExileEffect(final HellcarverDemonCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public HellcarverDemonCastFromExileEffect copy() {
        return new HellcarverDemonCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (targetPointer.getTargets(game, source).contains(sourceId)) {
            return game.getState().getZone(sourceId) == Zone.EXILED;
        }
        return false;
    }
}
