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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.other.OwnerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInOpponentsGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class DiluvianPrimordial extends CardImpl {

    public DiluvianPrimordial(UUID ownerId) {
        super(ownerId, 33, "Diluvian Primordial", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");
        this.expansionSetCode = "GTC";
        this.subtype.add("Avatar");

        this.color.setBlue(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Diluvian Primordial enters the battlefield, for each opponent, you may cast up to one target instant or sorcery card from that player's graveyard without paying its mana cost. If a card cast this way would be put into a graveyard this turn, exile it instead.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DiluvianPrimordialEffect(),false));
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof EntersBattlefieldTriggeredAbility) {
            ability.getTargets().clear();
            for(UUID opponentId : game.getOpponents(ability.getControllerId())) {
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null) {
                    FilterCard filter = new FilterCard(new StringBuilder("instant or sorcery card from ").append(opponent.getName()).append("'s graveyard").toString());
                    filter.add(new OwnerIdPredicate(opponentId));
                    filter.add(Predicates.or(new CardTypePredicate(CardType.INSTANT),new CardTypePredicate(CardType.SORCERY)));
                    TargetCardInOpponentsGraveyard target = new TargetCardInOpponentsGraveyard(0,1, filter);
                    ability.addTarget(target);
                }
            }
        }
    }

    public DiluvianPrimordial(final DiluvianPrimordial card) {
        super(card);
    }

    @Override
    public DiluvianPrimordial copy() {
        return new DiluvianPrimordial(this);
    }
}

class DiluvianPrimordialEffect extends OneShotEffect {

    public DiluvianPrimordialEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "for each opponent, you may cast up to one target instant or sorcery card from that player's graveyard without paying its mana cost. If a card cast this way would be put into a graveyard this turn, exile it instead";
    }

    public DiluvianPrimordialEffect(final DiluvianPrimordialEffect effect) {
        super(effect);
    }

    @Override
    public DiluvianPrimordialEffect copy() {
        return new DiluvianPrimordialEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        for (Target target: source.getTargets()) {
            if (target instanceof TargetCardInOpponentsGraveyard) {
                Card targetCard = game.getCard(target.getFirstTarget());
                if (controller != null && targetCard != null) {
                    if (controller.chooseUse(outcome, "Cast " + targetCard.getName() +"?", game)) {
                        controller.cast(targetCard.getSpellAbility(), game, true);
                        ContinuousEffect effect = new DiluvianPrimordialReplacementEffect();
                        effect.setTargetPointer(new FixedTarget(targetCard.getId()));
                        game.addEffect(effect, source);
                    }
                }
            }
        }
        return true;
    }
}

class DiluvianPrimordialReplacementEffect extends ReplacementEffectImpl {

    public DiluvianPrimordialReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.Exile);
        staticText = "If a card cast this way would be put into a graveyard this turn, exile it instead";
    }

    public DiluvianPrimordialReplacementEffect(final DiluvianPrimordialReplacementEffect effect) {
        super(effect);
    }

    @Override
    public DiluvianPrimordialReplacementEffect copy() {
        return new DiluvianPrimordialReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(getTargetPointer().getFirst(game, source));
            if (card != null) {
                controller.moveCardToGraveyardWithInfo(card, source.getSourceId(), game, Zone.STACK);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getToZone() == Zone.GRAVEYARD
                && ((ZoneChangeEvent) event).getTargetId().equals(getTargetPointer().getFirst(game, source))) {
            return true;
        }
        return false;
    }
}
