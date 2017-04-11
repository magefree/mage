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

import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class CruelReality extends CardImpl {

    public CruelReality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{5}{B}{B}");
        this.subtype.add("Aura");
        this.subtype.add("Curse");
        this.color.setBlack(true);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Damage));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        //At the beginning of enchanted player's upkeep, that player sacrifices a creature or planeswalker. If the player can't, he or she loses 5 life.
        this.addAbility(new CruelRealityTriggeredAbiilty());

    }

    public CruelReality(final CruelReality card) {
        super(card);
    }

    @Override
    public CruelReality copy() {
        return new CruelReality(this);
    }
}

class CruelRealityTriggeredAbiilty extends TriggeredAbilityImpl {

    public CruelRealityTriggeredAbiilty() {
        super(Zone.BATTLEFIELD, new CruelRealityEffect());
    }

    public CruelRealityTriggeredAbiilty(final CruelRealityTriggeredAbiilty ability) {
        super(ability);
    }

    @Override
    public CruelRealityTriggeredAbiilty copy() {
        return new CruelRealityTriggeredAbiilty(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(this.sourceId);
        if (enchantment != null
                && enchantment.getAttachedTo() != null) {
            Player cursedPlayer = game.getPlayer(enchantment.getAttachedTo());
            if (cursedPlayer != null
                    && game.getActivePlayerId().equals(cursedPlayer.getId())) {
                this.getEffects().get(0).setTargetPointer(new FixedTarget(cursedPlayer.getId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "At the beginning of enchanted player's upkeep, " + super.getRule();
    }
}

class CruelRealityEffect extends OneShotEffect {

    public CruelRealityEffect() {
        super(Outcome.LoseLife);
        staticText = "that player sacrifices a creature or planeswalker. If the player can't, he or she loses 5 life";
    }

    public CruelRealityEffect(final CruelRealityEffect effect) {
        super(effect);
    }

    @Override
    public CruelRealityEffect copy() {
        return new CruelRealityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player cursedPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (cursedPlayer != null
                && controller != null) {
            if (cursedPlayer.chooseUse(outcome, "Sacrifice a creature or planeswalker?", source, game)) {
                FilterControlledPermanent filter = new FilterControlledPermanent();
                filter.add(Predicates.or(
                        new CardTypePredicate(CardType.CREATURE),
                        new CardTypePredicate(CardType.PLANESWALKER)));
                TargetPermanent target = new TargetPermanent(filter);
                if (cursedPlayer.choose(outcome, target, source.getId(), game)) {
                    Permanent objectToBeSacrificed = game.getPermanent(target.getFirstTarget());
                    if (objectToBeSacrificed != null) {
                        if (objectToBeSacrificed.sacrifice(source.getId(), game)) {
                            return true;
                        }
                    }
                }
            }
            cursedPlayer.loseLife(5, game, false);
        }
        return false;
    }
}
