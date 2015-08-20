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
package mage.sets.fatereforged;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.ZoneChangeAllTriggeredAbility;
import mage.abilities.condition.common.ModeChoiceSourceCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseModeEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class OutpostSiege extends CardImpl {

    private final static String ruleTrigger1 = "&bull Khans &mdash; At the beginning of your upkeep, exile the top card of your library. Until end of turn, you may play that card.";
    private final static String ruleTrigger2 = "&bull Dragons &mdash; Whenever a creature you control leaves the battlefield, {this} deals 1 damage to target creature or player.";

    public OutpostSiege(UUID ownerId) {
        super(ownerId, 110, "Outpost Siege", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");
        this.expansionSetCode = "FRF";

        // As Outpost Siege enters the battlefield, choose Khans or Dragons.
        this.addAbility(new EntersBattlefieldAbility(new ChooseModeEffect("Khans or Dragons?", "Khans", "Dragons"), null, true,
                "As {this} enters the battlefield, choose Khans or Dragons.", ""));

        // * Khans - At the beginning of your upkeep, exile the top card of your library. Until end of turn, you may play that card.
        this.addAbility(new ConditionalTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(new OutpostSiegeExileEffect(), TargetController.YOU, false),
                new ModeChoiceSourceCondition("Khans"),
                ruleTrigger1));

        // * Dragons - Whenever a creature you control leaves the battlefield, Outpost Siege deals 1 damage to target creature or player.
        Ability ability2 = new ConditionalTriggeredAbility(
                new ZoneChangeAllTriggeredAbility(Zone.BATTLEFIELD, Zone.BATTLEFIELD, null, new DamageTargetEffect(1),
                        new FilterControlledCreaturePermanent(), "", false),
                new ModeChoiceSourceCondition("Dragons"),
                ruleTrigger2);
        ability2.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability2);

    }

    public OutpostSiege(final OutpostSiege card) {
        super(card);
    }

    @Override
    public OutpostSiege copy() {
        return new OutpostSiege(this);
    }
}

class OutpostSiegeExileEffect extends OneShotEffect {

    public OutpostSiegeExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile the top card of your library. Until end of turn, you may play that card";
    }

    public OutpostSiegeExileEffect(final OutpostSiegeExileEffect effect) {
        super(effect);
    }

    @Override
    public OutpostSiegeExileEffect copy() {
        return new OutpostSiegeExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                String exileName = sourcePermanent.getIdName() + " <this card may be played the turn it was exiled";
                controller.moveCardsToExile(card, source, game, true, source.getSourceId(), exileName);
                if (game.getState().getZone(card.getId()) == Zone.EXILED) {
                    ContinuousEffect effect = new CastFromNonHandZoneTargetEffect(Duration.EndOfTurn);
                    effect.setTargetPointer(new FixedTarget(card.getId(), card.getZoneChangeCounter(game)));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;
    }
}

class CastFromNonHandZoneTargetEffect extends AsThoughEffectImpl {

    public CastFromNonHandZoneTargetEffect(Duration duration) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, duration, Outcome.Benefit);
        staticText = "until end of turn, you may play that card";
    }

    public CastFromNonHandZoneTargetEffect(final CastFromNonHandZoneTargetEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public CastFromNonHandZoneTargetEffect copy() {
        return new CastFromNonHandZoneTargetEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (getTargetPointer().getTargets(game, source).contains(objectId)
                && source.getControllerId().equals(affectedControllerId)) {
            Card card = game.getCard(objectId);
            if (card != null) {
                return true;
            }
        }
        return false;
    }
}
