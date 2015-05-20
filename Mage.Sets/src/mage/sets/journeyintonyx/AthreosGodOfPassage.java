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
package mage.sets.journeyintonyx;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.LoseCreatureTypeSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.OwnerPredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class AthreosGodOfPassage extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature you own");
    
    static {
        filter.add(new AnotherPredicate());
        filter.add(new OwnerPredicate(TargetController.YOU));
    }
    
    public AthreosGodOfPassage(UUID ownerId) {
        super(ownerId, 146, "Athreos, God of Passage", Rarity.MYTHIC, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{W}{B}");
        this.expansionSetCode = "JOU";
        this.supertype.add("Legendary");
        this.subtype.add("God");

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());
        // As long as your devotion to white and black is less than seven, Athreos isn't a creature.
        Effect effect = new LoseCreatureTypeSourceEffect(new DevotionCount(ColoredManaSymbol.W, ColoredManaSymbol.B), 7);
        effect.setText("As long as your devotion to white and black is less than seven, Athreos isn't a creature");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));        
        // Whenever another creature you own dies, return it to your hand unless target opponent pays 3 life.
        Ability ability = new AthreosDiesCreatureTriggeredAbility(new AthreosGodOfPassageReturnEffect(), false, filter);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
        
    }

    public AthreosGodOfPassage(final AthreosGodOfPassage card) {
        super(card);
    }

    @Override
    public AthreosGodOfPassage copy() {
        return new AthreosGodOfPassage(this);
    }
}

class AthreosGodOfPassageReturnEffect extends OneShotEffect {
    
    public AthreosGodOfPassageReturnEffect() {
        super(Outcome.Benefit);
        this.staticText = "return it to your hand unless target opponent pays 3 life";
    }
    
    public AthreosGodOfPassageReturnEffect(final AthreosGodOfPassageReturnEffect effect) {
        super(effect);
    }
    
    @Override
    public AthreosGodOfPassageReturnEffect copy() {
        return new AthreosGodOfPassageReturnEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            UUID creatureId = (UUID) this.getValue("creatureId");
            Permanent creature = game.getPermanentOrLKIBattlefield(creatureId);
            if (creature != null) {
                Player opponent = game.getPlayer(source.getFirstTarget());
                boolean paid = false;
                if (opponent != null) {
                    Cost cost = new PayLifeCost(3);
                    if (cost.canPay(source, source.getSourceId(), opponent.getId(), game)
                            && opponent.chooseUse(outcome, new StringBuilder("Pay 3 live to prevent that ").append(creature.getLogName()).append(" returns to ").append(controller.getLogName()).append("'s hand?").toString(), game)) {
                        if (cost.pay(source, game, source.getSourceId(), opponent.getId(), false)) {
                            paid = true;
                        }
                    }            
                }
                if (opponent == null || !paid) {
                    if (game.getState().getZone(creature.getId()).equals(Zone.GRAVEYARD)) {
                        controller.moveCardToHandWithInfo(creature, source.getSourceId(), game, Zone.GRAVEYARD);
                    }
                }                
            }
            return true;
        }
        return false;
    }
}

class AthreosDiesCreatureTriggeredAbility extends TriggeredAbilityImpl {

    protected FilterCreaturePermanent filter;

    public AthreosDiesCreatureTriggeredAbility(Effect effect, boolean optional, FilterCreaturePermanent filter) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.filter = filter;
    }

    public AthreosDiesCreatureTriggeredAbility(AthreosDiesCreatureTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
    }

    @Override
    public AthreosDiesCreatureTriggeredAbility copy() {
        return new AthreosDiesCreatureTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType().equals(GameEvent.EventType.ZONE_CHANGE)) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getFromZone().equals(Zone.BATTLEFIELD) && zEvent.getToZone().equals(Zone.GRAVEYARD)) {
                Permanent permanent = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
                if (permanent != null && filter.match(permanent, sourceId, controllerId, game)) {
                    for (Effect effect : this.getEffects()) {
                        effect.setValue("creatureId", event.getTargetId());
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever " + filter.getMessage() + " dies, " + super.getRule();
    }
}
