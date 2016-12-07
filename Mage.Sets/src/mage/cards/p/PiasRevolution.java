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
package mage.cards.p;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.other.OwnerPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author JRHerlehy
 */
public class PiasRevolution extends CardImpl {

    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent("nontoken artifact");

    static {
        filter.add(Predicates.not(new TokenPredicate()));
        filter.add(new OwnerPredicate(TargetController.YOU));
    }

    public PiasRevolution(UUID ownerId, CardSetInfo setInfo) {

        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // Whenever a nontoken artifact is put into your graveyard from the battlefield, return that card to your hand unless target opponent has Pia's Revolution deal 3 damage to him or her.
        Ability ability = new PiasRevolutionArtifactToGraveyardTriggeredAbility(new PiasRevolutionReturnEffect(), false, filter);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public PiasRevolution(final PiasRevolution card) {
        super(card);
    }

    @Override
    public PiasRevolution copy() {
        return new PiasRevolution(this);
    }
}

class PiasRevolutionArtifactToGraveyardTriggeredAbility extends TriggeredAbilityImpl {

    protected FilterArtifactPermanent filter;

    public PiasRevolutionArtifactToGraveyardTriggeredAbility(Effect effect, boolean optional, FilterArtifactPermanent filter) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.filter = filter;
    }

    public PiasRevolutionArtifactToGraveyardTriggeredAbility(final PiasRevolutionArtifactToGraveyardTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getFromZone().equals(Zone.BATTLEFIELD) && zEvent.getToZone().equals(Zone.GRAVEYARD)) {
            Permanent permanent = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (permanent != null && filter.match(permanent, sourceId, controllerId, game)) {
                for (Effect effect : this.getEffects()) {
                    effect.setValue("artifactId", event.getTargetId());
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public PiasRevolutionArtifactToGraveyardTriggeredAbility copy() {
        return new PiasRevolutionArtifactToGraveyardTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever " + filter.getMessage() + " is put into your graveyard from the battlefield, " + super.getRule();
    }
}

class PiasRevolutionReturnEffect extends OneShotEffect {

    public PiasRevolutionReturnEffect() {
        super(Outcome.Benefit);
        this.staticText = "return it to your hand unless target opponent has {this} deal 3 damage to him or her";
    }

    public PiasRevolutionReturnEffect(final PiasRevolutionReturnEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());

        if (controller == null) return false;

        MageObject sourceObject = source.getSourceObject(game);
        UUID artifactId = (UUID) this.getValue("artifactId");
        Permanent artifact = game.getPermanentOrLKIBattlefield(artifactId);
        if (artifact != null) {
            Player opponent = game.getPlayer(source.getFirstTarget());
            boolean takeDamage = false;
            if (opponent != null) {
                String message = "Have " + sourceObject.getLogName() + " deal you 3 damage to prevent " + artifact.getName() + " returning to " + controller.getLogName() + "'s hand?";
                if (opponent.chooseUse(outcome, message, source, game)) takeDamage = true;
            }
            if (opponent == null || !takeDamage) {
                if (game.getState().getZone(artifact.getId()).equals(Zone.GRAVEYARD)) {
                    controller.moveCards(game.getCard(artifactId), Zone.HAND, source, game);
                }
            } else {
                opponent.damage(3, source.getSourceId(), game, false, true);
                game.informPlayers(opponent.getLogName() + " has " + sourceObject.getLogName() + " deal 3 damage to him or her");
            }
        }
        return true;
    }

    @Override
    public PiasRevolutionReturnEffect copy() {
        return new PiasRevolutionReturnEffect(this);
    }

}
