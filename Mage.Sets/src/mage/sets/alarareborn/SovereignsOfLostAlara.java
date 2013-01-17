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
package mage.sets.alarareborn;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ExaltedAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.other.CardCanTargetPermanentId;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public class SovereignsOfLostAlara extends CardImpl<SovereignsOfLostAlara> {

    public SovereignsOfLostAlara(UUID ownerId) {
        super(ownerId, 12, "Sovereigns of Lost Alara", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{W}{U}");
        this.expansionSetCode = "ARB";
        this.subtype.add("Spirit");

        this.color.setBlue(true);
        this.color.setWhite(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Exalted
        this.addAbility(new ExaltedAbility());

        // Whenever a creature you control attacks alone, you may search your library for an Aura card that could enchant that creature, put it onto the battlefield attached to that creature, then shuffle your library.
        this.addAbility(new CreatureControlledAttacksAloneTriggeredAbility());
    }

    public SovereignsOfLostAlara(final SovereignsOfLostAlara card) {
        super(card);
    }

    @Override
    public SovereignsOfLostAlara copy() {
        return new SovereignsOfLostAlara(this);
    }
}

class CreatureControlledAttacksAloneTriggeredAbility extends TriggeredAbilityImpl<CreatureControlledAttacksAloneTriggeredAbility> {

    public CreatureControlledAttacksAloneTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SovereignsOfLostAlaraEffect(), true);
    }

    public CreatureControlledAttacksAloneTriggeredAbility(final CreatureControlledAttacksAloneTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CreatureControlledAttacksAloneTriggeredAbility copy() {
        return new CreatureControlledAttacksAloneTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getActivePlayerId().equals(this.controllerId)) {
            if (event.getType() == EventType.DECLARED_ATTACKERS) {
                if (game.getCombat().attacksAlone()) {
                    TargetCreaturePermanent target = new TargetCreaturePermanent();
                    this.addTarget(target);
                    this.getTargets().get(0).add(game.getCombat().getAttackers().get(0), game);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control attacks alone, " + super.getRule();
    }
}

class SovereignsOfLostAlaraEffect extends OneShotEffect<SovereignsOfLostAlaraEffect> {

    public SovereignsOfLostAlaraEffect() {
        super(Constants.Outcome.BoostCreature);
        staticText = "you may search your library for an Aura card that could enchant that creature, put it onto the battlefield attached to that creature, then shuffle your library";
    }

    public SovereignsOfLostAlaraEffect(final SovereignsOfLostAlaraEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        Permanent attackingCreature = game.getPermanent(source.getFirstTarget());
        if (you != null && attackingCreature != null) {
            FilterCard filter = new FilterCard("aura that could enchant that creature");
            filter.add(new SubtypePredicate("Aura"));
            filter.add(new CardCanTargetPermanentId(attackingCreature.getId()));
            if (you.chooseUse(Constants.Outcome.Benefit, "Attach an Aura from your library?", game)) {
                TargetCardInLibrary target = new TargetCardInLibrary(filter);
                target.setNotTarget(true);
                if (you.searchLibrary(target, game)) {
                    Card aura = game.getCard(target.getFirstTarget());
                    game.getState().setValue("attachTo:" + aura.getId(), attackingCreature);
                    aura.putOntoBattlefield(game, Constants.Zone.LIBRARY, source.getId(), you.getId());
                    return attackingCreature.addAttachment(aura.getId(), game);
                }
            }
            you.shuffleLibrary(game);
        }
        return false;
    }

    @Override
    public SovereignsOfLostAlaraEffect copy() {
        return new SovereignsOfLostAlaraEffect(this);
    }
}