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
package mage.sets.darksteel;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import static mage.constants.Zone.GRAVEYARD;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 * @author Loki
 */
public class SwordOfLightAndShadow extends CardImpl {

    private static final FilterCard filter = new FilterCard("white and from black");

    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.WHITE),
                new ColorPredicate(ObjectColor.BLACK)));
    }

    public SwordOfLightAndShadow(UUID ownerId) {
        super(ownerId, 149, "Sword of Light and Shadow", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "DST";
        this.subtype.add("Equipment");

        // Equipped creature gets +2/+2 and has protection from white and from black.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(2, 2)));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(new ProtectionAbility(filter), AttachmentType.EQUIPMENT)));
        // Whenever equipped creature deals combat damage to a player, you gain 3 life and you may return up to one target creature card from your graveyard to your hand.
        this.addAbility(new SwordOfLightAndShadowAbility());
        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2)));
    }

    public SwordOfLightAndShadow(final SwordOfLightAndShadow card) {
        super(card);
    }

    @Override
    public SwordOfLightAndShadow copy() {
        return new SwordOfLightAndShadow(this);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {

        if (ability instanceof SwordOfLightAndShadowAbility) {
            Player controller = game.getPlayer(ability.getControllerId());
            if (controller != null) {
                // Target may only be added if possible target exists. Else the gain life effect won't trigger, becuase there is no valid target for the
                // return to hand ability
                if (controller.getGraveyard().count(new FilterCreatureCard(), ability.getSourceId(), ability.getControllerId(), game) > 0) {
                    ability.addTarget(new TargetCardInYourGraveyard(0, 1, new FilterCreatureCard("creature card from your graveyard")));
                }
            }
        }

    }
}

class SwordOfLightAndShadowAbility extends TriggeredAbilityImpl {

    public SwordOfLightAndShadowAbility() {
        super(Zone.BATTLEFIELD, new SwordOfLightAndShadowReturnToHandTargetEffect(), false);
        this.addEffect(new GainLifeEffect(3));

    }

    public SwordOfLightAndShadowAbility(final SwordOfLightAndShadowAbility ability) {
        super(ability);
    }

    @Override
    public SwordOfLightAndShadowAbility copy() {
        return new SwordOfLightAndShadowAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
        Permanent p = game.getPermanent(event.getSourceId());
        return damageEvent.isCombatDamage() && p != null && p.getAttachments().contains(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "Whenever equipped creature deals combat damage to a player, you gain 3 life and you may return up to one target creature card from your graveyard to your hand.";
    }
}

class SwordOfLightAndShadowReturnToHandTargetEffect extends OneShotEffect {

    public SwordOfLightAndShadowReturnToHandTargetEffect() {
        super(Outcome.ReturnToHand);
        staticText = "and you may return up to one target creature card from your graveyard to your hand";
    }

    public SwordOfLightAndShadowReturnToHandTargetEffect(final SwordOfLightAndShadowReturnToHandTargetEffect effect) {
        super(effect);
    }

    @Override
    public SwordOfLightAndShadowReturnToHandTargetEffect copy() {
        return new SwordOfLightAndShadowReturnToHandTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = true; // in case no target is selected
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        if (!source.getTargets().isEmpty() && targetPointer.getFirst(game, source) != null) {
            if (controller.chooseUse(outcome, "Return creature card from graveyard to hand?", source, game)) {
                for (UUID targetId : targetPointer.getTargets(game, source)) {
                    switch (game.getState().getZone(targetId)) {
                        case GRAVEYARD:
                            Card card = game.getCard(targetId);
                            if (card != null) {
                                controller.moveCards(card, null, Zone.HAND, source, game);
                            } else {
                                result = false;
                            }
                            break;
                    }
                }
            }
        }
        return result;
    }

}
