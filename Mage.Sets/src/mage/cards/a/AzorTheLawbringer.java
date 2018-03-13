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
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2 & L_J
 */
public class AzorTheLawbringer extends CardImpl {

    public AzorTheLawbringer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}{U}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Azor, the Lawbringer enters the battlefield, each opponent can’t cast instant or sorcery spells during that player’s next turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AzorTheLawbringerEntersBattlefieldEffect(), false));

        // Whenever Azor attacks, you may pay {X}{W}{U}{U}. If you do, you gain X life and draw X cards.
        this.addAbility(new AttacksTriggeredAbility(new AzorTheLawbringerAttacksEffect(), false));
    }

    public AzorTheLawbringer(final AzorTheLawbringer card) {
        super(card);
    }

    @Override
    public AzorTheLawbringer copy() {
        return new AzorTheLawbringer(this);
    }
}

class AzorTheLawbringerEntersBattlefieldEffect extends OneShotEffect {

    public AzorTheLawbringerEntersBattlefieldEffect() {
        super(Outcome.Benefit);
        this.staticText = "each opponent can't cast instant or sorcery spells during that player's next turn";
    }

    public AzorTheLawbringerEntersBattlefieldEffect(final AzorTheLawbringerEntersBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public AzorTheLawbringerEntersBattlefieldEffect copy() {
        return new AzorTheLawbringerEntersBattlefieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            ContinuousEffect effect = new AzorTheLawbringerCantCastEffect();
            effect.setTargetPointer(new FixedTarget(opponentId));
            game.addEffect(effect, source);
        }
        return true;
    }
}

class AzorTheLawbringerCantCastEffect extends ContinuousRuleModifyingEffectImpl {

    int playersNextTurn;

    public AzorTheLawbringerCantCastEffect() {
        super(Duration.Custom, Outcome.Detriment);
        staticText = "You can't cast instant or sorcery spells during this turn";
        playersNextTurn = 0;
    }

    public AzorTheLawbringerCantCastEffect(final AzorTheLawbringerCantCastEffect effect) {
        super(effect);
        this.playersNextTurn = effect.playersNextTurn;
    }

    @Override
    public AzorTheLawbringerCantCastEffect copy() {
        return new AzorTheLawbringerCantCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source.getSourceId());
        if (mageObject != null) {
            return "You can't cast instant or sorcery spells this turn (" + mageObject.getIdName() + ").";
        }
        return null;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        UUID opponentId = getTargetPointer().getFirst(game, source);
        if (game.getActivePlayerId().equals(opponentId)) {
            if (playersNextTurn == 0) {
                playersNextTurn = game.getTurnNum();
            }
            if (playersNextTurn == game.getTurnNum()) {
                if (opponentId.equals(event.getPlayerId())) {
                    MageObject object = game.getObject(event.getSourceId());
                    if (event.getType() == GameEvent.EventType.CAST_SPELL) {
                        if (object.isInstant() || object.isSorcery()) {
                            return true;
                        }
                    }
                }
            } else {
                discard();
            }
        } else if (playersNextTurn > 0) {
            discard();
        }
        return false;
    }
}

class AzorTheLawbringerAttacksEffect extends OneShotEffect {

    AzorTheLawbringerAttacksEffect() {
        super(Outcome.Benefit);
        staticText = "you may pay {X}{W}{U}{U}. If you do, you gain X life and draw X cards";
    }

    AzorTheLawbringerAttacksEffect(final AzorTheLawbringerAttacksEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ManaCosts cost = new ManaCostsImpl("{X}{W}{U}{U}");
            if (controller.chooseUse(Outcome.Damage, "Pay " + cost.getText() + "? If you do, you gain X life and draw X cards.", source, game)) {
                int costX = controller.announceXMana(0, Integer.MAX_VALUE, "Announce the value for {X}", game, source);
                cost.add(new GenericManaCost(costX));
                if (cost.pay(source, game, source.getSourceId(), source.getControllerId(), false, null)) {
                    controller.resetStoredBookmark(game); // otherwise you can undo the payment
                    controller.gainLife(costX, game);
                    controller.drawCards(costX, game);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public AzorTheLawbringerAttacksEffect copy() {
        return new AzorTheLawbringerAttacksEffect(this);
    }

}
