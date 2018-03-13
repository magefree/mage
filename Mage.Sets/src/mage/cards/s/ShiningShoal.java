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
 *  CONTRIBUTORS BE LIAB8LE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
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
package mage.cards.s;

import java.util.UUID;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ExileFromHandCostCardConvertedMana;
import mage.abilities.effects.common.RedirectDamageFromSourceToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterOwnedCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetSource;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author LevelX2
 */
public class ShiningShoal extends CardImpl {

    public ShiningShoal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{W}{W}");
        this.subtype.add(SubType.ARCANE);

        // You may exile a white card with converted mana cost X from your hand rather than pay Shining Shoal's mana cost
        FilterOwnedCard filter = new FilterOwnedCard("a white card with converted mana cost X from your hand");
        filter.add(new ColorPredicate(ObjectColor.WHITE));
        filter.add(Predicates.not(new CardIdPredicate(this.getId()))); // the exile cost can never be paid with the card itself
        this.addAbility(new AlternativeCostSourceAbility(new ExileFromHandCost(new TargetCardInHand(filter), true)));

        // The next X damage that a source of your choice would deal to you and/or creatures you control this turn is dealt to target creature or player instead.
        this.getSpellAbility().addEffect(new ShiningShoalRedirectDamageTargetEffect(Duration.EndOfTurn, new ExileFromHandCostCardConvertedMana()));
        this.getSpellAbility().addTarget(new TargetSource());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlayer());
    }

    public ShiningShoal(final ShiningShoal card) {
        super(card);
    }

    @Override
    public ShiningShoal copy() {
        return new ShiningShoal(this);
    }
}

class ShiningShoalRedirectDamageTargetEffect extends RedirectDamageFromSourceToTargetEffect {

    private final DynamicValue dynamicAmount;

    public ShiningShoalRedirectDamageTargetEffect(Duration duration, DynamicValue dynamicAmount) {
        super(duration, 0, true);
        this.dynamicAmount = dynamicAmount;
        staticText = "The next X damage that a source of your choice would deal to you and/or creatures you control this turn is dealt to target creature or player instead";
    }

    public ShiningShoalRedirectDamageTargetEffect(final ShiningShoalRedirectDamageTargetEffect effect) {
        super(effect);
        this.dynamicAmount = effect.dynamicAmount;
    }

    @Override
    public ShiningShoalRedirectDamageTargetEffect copy() {
        return new ShiningShoalRedirectDamageTargetEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        amountToRedirect = dynamicAmount.calculate(game, source, this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!this.used && event.getFlag()) {

            // get source of the damage event
            MageObject sourceObject = game.getObject(event.getSourceId());
            // get the chosen damage source
            MageObject chosenSourceObject = game.getObject(source.getFirstTarget());
            // does the source of the damage exist?
            if (sourceObject == null) {
                game.informPlayers("Couldn't find source of damage");
                return false;
            }
            // do the 2 objects match?
            if (!sourceObject.getId().equals(chosenSourceObject.getId())) {
                return false;
            }

            // check target
            //   check creature first
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null && permanent.isCreature()) {
                if (permanent.getControllerId().equals(source.getControllerId())) {
                    // it's your creature
                    redirectTarget = source.getTargets().get(1);
                    return true;
                }
            }
            //   check player
            Player player = game.getPlayer(event.getTargetId());
            if (player != null) {
                if (player.getId().equals(source.getControllerId())) {
                    // it is you
                    redirectTarget = source.getTargets().get(1);
                    return true;
                }
            }
        }
        return false;
    }

}
