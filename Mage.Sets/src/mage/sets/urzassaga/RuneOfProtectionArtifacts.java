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
package mage.sets.urzassaga;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterObject;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetSource;

/**
 *
 * @author fireshoes
 */
public class RuneOfProtectionArtifacts extends CardImpl {

    public RuneOfProtectionArtifacts(UUID ownerId) {
        super(ownerId, 35, "Rune of Protection: Artifacts", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");
        this.expansionSetCode = "USG";


		// {W}: The next time a black source of your choice would deal damage to you this turn, prevent that damage.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RuneOfProtectionArtifactsEffect() , new ManaCostsImpl("W")));
		// Cycling {2} ({2}, Discard this card: Draw a card.)
		this.addAbility(new CyclingAbility(new ManaCostsImpl("{2}")));
    }

    public RuneOfProtectionArtifacts(final RuneOfProtectionArtifacts card) {
        super(card);
    }

    @Override
    public RuneOfProtectionArtifacts copy() {
        return new RuneOfProtectionArtifacts(this);
    }
}

class RuneOfProtectionArtifactsEffect extends PreventionEffectImpl {

    private static final FilterObject filter = new FilterObject("artifact source");

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
    } 

    private TargetSource target;

    public RuneOfProtectionArtifactsEffect() {
        super(Duration.EndOfTurn);
        target = new TargetSource(filter);
        
        staticText = "The next time an artifact source of your choice would deal damage to you this turn, prevent that damage";
    }

    public RuneOfProtectionArtifactsEffect(final RuneOfProtectionArtifactsEffect effect) {
        super(effect);
        this.target = effect.target.copy();
    }

    @Override
    public RuneOfProtectionArtifactsEffect copy() {
        return new RuneOfProtectionArtifactsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public void init(Ability source, Game game) {
        this.target.choose(Outcome.PreventDamage, source.getControllerId(), source.getSourceId(), game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(source.getControllerId()) && event.getSourceId().equals(target.getFirstTarget())) {
            preventDamage(event, source, target.getFirstTarget(), game);
            return true;
        }
        return false;
    }

    private void preventDamage(GameEvent event, Ability source, UUID target, Game game) {
        GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, target, source.getSourceId(), source.getControllerId(), event.getAmount(), false);
        if (!game.replaceEvent(preventEvent)) {
            int damage = event.getAmount();
            event.setAmount(0);
            this.used = true;
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, target, source.getSourceId(), source.getControllerId(), damage));
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!this.used && super.applies(event, source, game)) {
            if (event.getTargetId().equals(source.getControllerId()) && event.getSourceId().equals(target.getFirstTarget())) {
                return true;
            }
        }
        return false;
    }

}