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
package mage.sets.scarsofmirrodin;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.ReturnFromExileEffect;
import mage.abilities.effects.common.combat.CantBeBlockedAllEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.OwnerPredicate;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author nantuko
 */
public class VenserTheSojourner extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("permanent you own");

    static {
        filter.add(new OwnerPredicate(TargetController.YOU));
    }

    public VenserTheSojourner(UUID ownerId) {
        super(ownerId, 135, "Venser, the Sojourner", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{3}{W}{U}");
        this.expansionSetCode = "SOM";
        this.subtype.add("Venser");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(3));

        // +2: Exile target permanent you own. Return it to the battlefield under your control at the beginning of the next end step.
        LoyaltyAbility ability1 = new LoyaltyAbility(new VenserTheSojournerEffect(), 2);
        Target target = new TargetPermanent(filter);
        ability1.addTarget(target);
        this.addAbility(ability1);

        // -1: Creatures can't be blocked this turn.
        this.addAbility(new LoyaltyAbility(new CantBeBlockedAllEffect(new FilterCreaturePermanent("Creatures"), Duration.EndOfTurn), -1));

        // -8: You get an emblem with "Whenever you cast a spell, exile target permanent."
        LoyaltyAbility ability2 = new LoyaltyAbility(new GetEmblemEffect(new VenserTheSojournerEmblem()), -8);
        this.addAbility(ability2);
    }

    public VenserTheSojourner(final VenserTheSojourner card) {
        super(card);
    }

    @Override
    public VenserTheSojourner copy() {
        return new VenserTheSojourner(this);
    }

}

class VenserTheSojournerEffect extends OneShotEffect {

    private static final String effectText = "Exile target permanent you own. Return it to the battlefield under your control at the beginning of the next end step";

    VenserTheSojournerEffect() {
        super(Outcome.Benefit);
        staticText = effectText;
    }

    VenserTheSojournerEffect(VenserTheSojournerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            if (getTargetPointer().getFirst(game, source) != null) {
                Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
                if (permanent != null) {
                    if (controller.moveCardToExileWithInfo(permanent, source.getSourceId(), sourceObject.getIdName(), source.getSourceId(), game, Zone.BATTLEFIELD, true)) {
                        //create delayed triggered ability
                        AtTheBeginOfNextEndStepDelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new ReturnFromExileEffect(source.getSourceId(), Zone.BATTLEFIELD));
                        delayedAbility.setSourceId(source.getSourceId());
                        delayedAbility.setControllerId(source.getControllerId());
                        delayedAbility.setSourceObject(source.getSourceObject(game), game);
                        game.addDelayedTriggeredAbility(delayedAbility);
                        return true;
                    }
                }
            }

        }
        return false;
    }

    @Override
    public VenserTheSojournerEffect copy() {
        return new VenserTheSojournerEffect(this);
    }

}

/**
 * Emblem: "Whenever you cast a spell, exile target permanent."
 */
class VenserTheSojournerEmblem extends Emblem {

    public VenserTheSojournerEmblem() {
        this.setName("EMBLEM: Venser, the Sojourner");
        Ability ability = new VenserTheSojournerSpellCastTriggeredAbility(new ExileTargetEffect(), false);
        Target target = new TargetPermanent();
        ability.addTarget(target);
        this.getAbilities().add(ability);
    }
}

class VenserTheSojournerSpellCastTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterSpell spellCard = new FilterSpell("a spell");
    protected FilterSpell filter;

    /**
     * If true, the source that triggered the ability will be set as target to
     * effect.
     */
    protected boolean rememberSource = false;

    public VenserTheSojournerSpellCastTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.COMMAND, effect, optional);
        this.filter = spellCard;
    }

    public VenserTheSojournerSpellCastTriggeredAbility(final VenserTheSojournerSpellCastTriggeredAbility ability) {
        super(ability);
        filter = ability.filter;
        this.rememberSource = ability.rememberSource;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && filter.match(spell, game)) {
                if (rememberSource) {
                    this.getEffects().get(0).setTargetPointer(new FixedTarget(spell.getId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast a spell, exile target permanent.";
    }

    @Override
    public VenserTheSojournerSpellCastTriggeredAbility copy() {
        return new VenserTheSojournerSpellCastTriggeredAbility(this);
    }
}
