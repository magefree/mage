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
package mage.sets.torment;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class LlawanCephalidEmpress extends CardImpl<LlawanCephalidEmpress> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("blue creatures your opponents control");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    public LlawanCephalidEmpress(UUID ownerId) {
        super(ownerId, 42, "Llawan, Cephalid Empress", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.expansionSetCode = "TOR";
        this.supertype.add("Legendary");
        this.subtype.add("Cephalid");

        this.color.setBlue(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Llawan, Cephalid Empress enters the battlefield, return all blue creatures your opponents control to their owners' hands.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ReturnToHandFromBattlefieldAllEffect(filter), false));

        // Your opponents can't cast blue creature spells.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LlawanCephalidEmpressReplacementEffect()));
    }

    public LlawanCephalidEmpress(final LlawanCephalidEmpress card) {
        super(card);
    }

    @Override
    public LlawanCephalidEmpress copy() {
        return new LlawanCephalidEmpress(this);
    }
}


class LlawanCephalidEmpressReplacementEffect extends ReplacementEffectImpl<LlawanCephalidEmpressReplacementEffect> {

    private static final FilterCard filter = new FilterCard("blue creature spells");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    public LlawanCephalidEmpressReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Your opponents can't cast blue creature spells";
    }

    public LlawanCephalidEmpressReplacementEffect(final LlawanCephalidEmpressReplacementEffect effect) {
        super(effect);
    }

    @Override
    public LlawanCephalidEmpressReplacementEffect copy() {
        return new LlawanCephalidEmpressReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.CAST_SPELL) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null && game.isOpponent(null, event.getPlayerId())) {
                Card card = game.getCard(event.getSourceId());
                if (card != null && filter.match(card, source.getControllerId(), game)) {
                    return true;
                }
            }
        }
        return false;
    }

}
