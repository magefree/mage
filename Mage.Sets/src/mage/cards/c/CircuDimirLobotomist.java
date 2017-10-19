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
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterPlayer;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class CircuDimirLobotomist extends CardImpl {

    private static final FilterSpell filterBlue = new FilterSpell("a blue spell");
    private static final FilterSpell filterBlack = new FilterSpell("a black spell");

    static {
        filterBlue.add(new ColorPredicate(ObjectColor.BLUE));
        filterBlack.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public CircuDimirLobotomist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // 10/1/2005 	The first two abilities target libraries, not players.
        // Target Library not supported yet - used as workaround target player
        // Whenever you cast a blue spell, exile the top card of target library.
        Ability ability = new SpellCastControllerTriggeredAbility(new CircuDimirLobotomistEffect(), filterBlue, false);
        ability.addTarget(new TargetPlayer(1, 1, true, new FilterPlayer("target library")));
        this.addAbility(ability);
        // Whenever you cast a black spell, exile the top card of target library.
        ability = new SpellCastControllerTriggeredAbility(new CircuDimirLobotomistEffect(), filterBlack, false);
        ability.addTarget(new TargetPlayer(1, 1, true, new FilterPlayer("target library")));
        this.addAbility(ability);
        // Your opponents can't cast nonland cards with the same name as a card exiled with Circu, Dimir Lobotomist.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CircuDimirLobotomistRuleModifyingEffect()));
    }

    public CircuDimirLobotomist(final CircuDimirLobotomist card) {
        super(card);
    }

    @Override
    public CircuDimirLobotomist copy() {
        return new CircuDimirLobotomist(this);
    }
}

class CircuDimirLobotomistEffect extends OneShotEffect {

    public CircuDimirLobotomistEffect() {
        super(Outcome.Detriment);
        this.staticText = "exile the top card of target library";
    }

    public CircuDimirLobotomistEffect(final CircuDimirLobotomistEffect effect) {
        super(effect);
    }

    @Override
    public CircuDimirLobotomistEffect copy() {
        return new CircuDimirLobotomistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player playerTargetLibrary = game.getPlayer(getTargetPointer().getFirst(game, source));
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && playerTargetLibrary != null && sourcePermanent != null) {
            UUID exileId = CardUtil.getCardExileZoneId(game, source);
            controller.moveCardToExileWithInfo(playerTargetLibrary.getLibrary().getFromTop(game), exileId,
                    sourcePermanent.getIdName() + " (" + sourcePermanent.getZoneChangeCounter(game) + ')', source.getSourceId(), game, Zone.BATTLEFIELD, true);
            return true;
        }
        return false;
    }
}

class CircuDimirLobotomistRuleModifyingEffect extends ContinuousRuleModifyingEffectImpl {

    public CircuDimirLobotomistRuleModifyingEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Your opponents can't cast nonland cards with the same name as a card exiled with {this}";
    }

    public CircuDimirLobotomistRuleModifyingEffect(final CircuDimirLobotomistRuleModifyingEffect effect) {
        super(effect);
    }

    @Override
    public CircuDimirLobotomistRuleModifyingEffect copy() {
        return new CircuDimirLobotomistRuleModifyingEffect(this);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source.getSourceId());
        if (mageObject != null) {
            return "You can't cast this spell because a card with the same name is exiled by " + mageObject.getLogName() + '.';
        }
        return null;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == EventType.CAST_SPELL && game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
            MageObject object = game.getObject(event.getSourceId());
            if (object != null) {
                ExileZone exileZone = game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, source));
                if ((exileZone != null)) {
                    for (Card card : exileZone.getCards(game)) {
                        if ((card.getName().equals(object.getName()))) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
