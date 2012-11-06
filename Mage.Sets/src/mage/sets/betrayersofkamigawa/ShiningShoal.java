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
package mage.sets.betrayersofkamigawa;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.costs.AlternativeCostImpl;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ExileFromHandCostCardConvertedMana;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
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
public class ShiningShoal extends CardImpl<ShiningShoal> {

    private static final String ALTERNATIVE_COST_DESCRIPTION = "You may exile a white card with converted mana cost X from your hand rather than pay Shining Shoal's mana cost";

    public ShiningShoal(UUID ownerId) {
        super(ownerId, 21, "Shining Shoal", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{X}{W}{W}");
        this.expansionSetCode = "BOK";
        this.subtype.add("Arcane");
        this.color.setWhite(true);

        // You may exile a white card with converted mana cost X from your hand rather than pay Shining Shoal's mana cost
        FilterOwnedCard filter = new FilterOwnedCard("white card from your hand");
        filter.add(new ColorPredicate(ObjectColor.WHITE));
        filter.add(Predicates.not(new CardIdPredicate(this.getId()))); // the exile cost can never be paid with the card itself
        this.getSpellAbility().addAlternativeCost(new AlternativeCostImpl(ALTERNATIVE_COST_DESCRIPTION, new ExileFromHandCost(new TargetCardInHand(filter))));

        // The next X damage that a source of your choice would deal to you and/or creatures you control this turn is dealt to target creature or player instead.
        this.getSpellAbility().addEffect(new ShiningShoalPreventDamageTargetEffect(Constants.Duration.EndOfTurn, new ExileFromHandCostCardConvertedMana()));
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


class ShiningShoalPreventDamageTargetEffect extends PreventionEffectImpl<ShiningShoalPreventDamageTargetEffect> {

    private DynamicValue dynamicAmount;
    private int amount;

    public ShiningShoalPreventDamageTargetEffect(Constants.Duration duration, DynamicValue dynamicAmount) {
        super(duration);
        this.dynamicAmount = dynamicAmount;
        staticText = "The next X damage that a source of your choice would deal to you and/or creatures you control this turn is dealt to target creature or player instead";
    }

    public ShiningShoalPreventDamageTargetEffect(final ShiningShoalPreventDamageTargetEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.dynamicAmount = effect.dynamicAmount;
    }

    @Override
    public ShiningShoalPreventDamageTargetEffect copy() {
        return new ShiningShoalPreventDamageTargetEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        this.amount = dynamicAmount.calculate(game, source);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, source.getFirstTarget(), source.getId(), source.getControllerId(), event.getAmount(), false);
        if (!game.replaceEvent(preventEvent)) {
            int prevented = 0;
            if (event.getAmount() >= this.amount) {
                int damage = amount;
                event.setAmount(event.getAmount() - amount);
                this.used = true;
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, source.getFirstTarget(), source.getId(), source.getControllerId(), damage));
                prevented = damage;
            } else {
                int damage = event.getAmount();
                event.setAmount(0);
                amount -= damage;
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, source.getFirstTarget(), source.getId(), source.getControllerId(), damage));
                prevented = damage;
            }

            // deal damage now
            if (prevented > 0) {
                UUID redirectTo = source.getTargets().get(1).getFirstTarget();
                Permanent permanent = game.getPermanent(redirectTo);
                if (permanent != null) {
                    game.informPlayers("Dealing " + prevented + " to " + permanent.getName() + " instead");
                    // keep the original source id as it is redirecting
                    event.getAppliedEffects().add(getId());
                    permanent.damage(prevented, event.getSourceId(), game, true, false, event.getAppliedEffects());
                }
                Player player = game.getPlayer(redirectTo);
                if (player != null) {
                    game.informPlayers("Dealing " + prevented + " to " + player.getName() + " instead");
                    // keep the original source id as it is redirecting
                    event.getAppliedEffects().add(getId());
                    player.damage(prevented, event.getSourceId(), game, true, false, event.getAppliedEffects());
                }
            }
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!this.used && super.applies(event, source, game) && !event.getAppliedEffects().contains(getId())) {

            // check source
            MageObject object = game.getObject(event.getSourceId());
            if (object == null) {
                game.informPlayers("Couldn't find source of damage");
                return false;
            }

            if (!object.getId().equals(source.getFirstTarget())) {
                return false;
            }

            // check target
            //   check creature first
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null && permanent.getCardType().contains(CardType.CREATURE)) {
                if (permanent.getControllerId().equals(source.getControllerId())) {
                    // it's your creature
                    return true;
                }
            }
            //   check player
            Player player = game.getPlayer(event.getTargetId());
            if (player != null) {
                if (player.getId().equals(source.getControllerId())) {
                    // it is you
                    return true;
                }
            }
        }
        return false;
    }

}