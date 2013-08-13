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
package mage.sets.championsofkamigawa;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TurnPhase;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.turn.TurnMod;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class GodoBanditWarlord extends CardImpl<GodoBanditWarlord> {

    private static final FilterCard filter = new FilterCard("an Equipment card");
    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
        filter.add(new SubtypePredicate("Equipment"));
    }

    public GodoBanditWarlord(UUID ownerId) {
        super(ownerId, 169, "Godo, Bandit Warlord", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{R}");
        this.expansionSetCode = "CHK";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Barbarian");

        this.color.setRed(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Godo, Bandit Warlord enters the battlefield, you may search your library for an Equipment card and put it onto the battlefield. If you do, shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter), false, true), true));
        // Whenever Godo attacks for the first time each turn, untap it and all Samurai you control. After this phase, there is an additional combat phase.
        FilterControlledCreaturePermanent untapFilter = new FilterControlledCreaturePermanent();
        untapFilter.add(Predicates.or(new PermanentIdPredicate(this.getId()), new SubtypePredicate("Samurai")));
        Ability ability = new GodoBanditWarlordAttacksTriggeredAbility(new UntapAllControllerEffect(untapFilter,"untap it and all Samurai you control"), false);
        ability.addEffect(new AdditionalCombatPhaseEffect());
        this.addAbility(ability);
    }

    public GodoBanditWarlord(final GodoBanditWarlord card) {
        super(card);
    }

    @Override
    public GodoBanditWarlord copy() {
        return new GodoBanditWarlord(this);
    }
}

class GodoBanditWarlordAttacksTriggeredAbility extends TriggeredAbilityImpl<GodoBanditWarlordAttacksTriggeredAbility> {

    public GodoBanditWarlordAttacksTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    public GodoBanditWarlordAttacksTriggeredAbility(final GodoBanditWarlordAttacksTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public void reset(Game game) {
        game.getState().setValue(CardUtil.getCardZoneString("amountAttacks", this.getSourceId(), game), new Integer(0));
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
       if (event.getType() == EventType.ATTACKER_DECLARED && event.getSourceId().equals(this.getSourceId()) ) {
           Integer amountAttacks = (Integer) game.getState().getValue(CardUtil.getCardZoneString("amountAttacks", this.getSourceId(), game));
           if (amountAttacks == null || amountAttacks.intValue() < 1) {
               if (amountAttacks == null) {
                   amountAttacks = new Integer(1);
               } else {
                   ++amountAttacks;
               }
               game.getState().setValue(CardUtil.getCardZoneString("amountAttacks", this.getSourceId(), game), amountAttacks);
               return true;
           }
       }
       return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} attacks for the first time each turn, " + super.getRule();
    }

    @Override
    public GodoBanditWarlordAttacksTriggeredAbility copy() {
       return new GodoBanditWarlordAttacksTriggeredAbility(this);
    }
}

class AdditionalCombatPhaseEffect extends OneShotEffect<AdditionalCombatPhaseEffect> {

    public AdditionalCombatPhaseEffect() {
       super(Outcome.Benefit);
       staticText = "After this phase, there is an additional combat phase";
    }

    public AdditionalCombatPhaseEffect(final AdditionalCombatPhaseEffect effect) {
       super(effect);
    }

    @Override
    public AdditionalCombatPhaseEffect copy() {
       return new AdditionalCombatPhaseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
       game.getState().getTurnMods().add(new TurnMod(source.getControllerId(), TurnPhase.COMBAT, null, false));
       return true;
    }
}
