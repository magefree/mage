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
package mage.sets.mirage;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SkipUntapOptionalAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author Quercitron
 */
public class AmberPrison extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact, creature, or land");
    
    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.LAND)));
    }
    
    public AmberPrison(UUID ownerId) {
        super(ownerId, 257, "Amber Prison", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.expansionSetCode = "MIR";

        // You may choose not to untap Amber Prison during your untap step.
        this.addAbility(new SkipUntapOptionalAbility());
        
        // {4}, {tap}: Tap target artifact, creature, or land. That permanent doesn't untap during its controller's untap step for as long as Amber Prison remains tapped.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AmberPrisonTapTargetEffect(), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
        this.addAbility(new AmberPrisonUntapTriggeredAbility());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AmberPrisonRestrictionEffect()));
    }

    public AmberPrison(final AmberPrison card) {
        super(card);
    }

    @Override
    public AmberPrison copy() {
        return new AmberPrison(this);
    }
}

class AmberPrisonTapTargetEffect extends OneShotEffect {

    public AmberPrisonTapTargetEffect() {
        super(Outcome.Tap);
        this.staticText = "Tap target artifact, creature, or land. That permanent doesn't untap during its controller's untap step for as long as {source} remains tapped.";
    }
    
    public AmberPrisonTapTargetEffect(final AmberPrisonTapTargetEffect effect) {
        super(effect);
    }

    @Override
    public AmberPrisonTapTargetEffect copy() {
        return new AmberPrisonTapTargetEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        for (UUID target : targetPointer.getTargets(game, source)) {
            Permanent permanent = game.getPermanent(target);
            if (permanent != null) {
                if (sourcePermanent != null) {
                    sourcePermanent.addConnectedCard("AmberPrison", permanent.getId());
                }
                permanent.tap(game);
            }
        }
        return true;
    }
    
}

class AmberPrisonRestrictionEffect extends RestrictionEffect {

    public AmberPrisonRestrictionEffect() {
        super(Duration.WhileOnBattlefield);
    }
    
    public AmberPrisonRestrictionEffect(final AmberPrisonRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public AmberPrisonRestrictionEffect copy() {
        return new AmberPrisonRestrictionEffect(this);
    }
    
    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null && sourcePermanent.isTapped()) {
            if (sourcePermanent.getConnectedCards("AmberPrison") != null) {
                return sourcePermanent.getConnectedCards("AmberPrison").contains(permanent.getId());
            }
        }
        return false;
    }

    @Override
    public boolean canBeUntapped(Permanent permanent, Game game) {
        return false;
    }
    
}


class AmberPrisonUntapTriggeredAbility extends TriggeredAbilityImpl {

    public AmberPrisonUntapTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AmberPrisonReleaseOnUntapEffect(), false);
        this.usesStack = false;
        this.ruleVisible = false;
    }
    
    public AmberPrisonUntapTriggeredAbility(final AmberPrisonUntapTriggeredAbility ability) {
        super(ability);
    }
    
    @Override
    public AmberPrisonUntapTriggeredAbility copy() {
        return new AmberPrisonUntapTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.UNTAP;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(this.getSourceId());
    }
}

class AmberPrisonReleaseOnUntapEffect extends OneShotEffect {

    public AmberPrisonReleaseOnUntapEffect() {
        super(Outcome.Detriment);
    }
    
    public AmberPrisonReleaseOnUntapEffect(final AmberPrisonReleaseOnUntapEffect effect) {
        super(effect);
    }

    @Override
    public AmberPrisonReleaseOnUntapEffect copy() {
        return new AmberPrisonReleaseOnUntapEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null) {
            sourcePermanent.clearConnectedCards("AmberPrison");
            return true;
        }
        return false;
    }
    
}
