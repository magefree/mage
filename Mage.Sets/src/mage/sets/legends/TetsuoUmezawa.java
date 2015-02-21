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
package mage.sets.legends;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousRuleModifiyingEffectImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.BlockingPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author JRHerlehy
 */
public class TetsuoUmezawa extends CardImpl {

    private static final FilterCreaturePermanent creatureFilter = new FilterCreaturePermanent("tapped or blocking creature");

    static {
        creatureFilter.add(Predicates.or(
                new TappedPredicate(),
                new BlockingPredicate()));
    }

    public TetsuoUmezawa(UUID ownerId) {
        super(ownerId, 302, "Tetsuo Umezawa", Rarity.RARE, new CardType[]{
            CardType.CREATURE
        }, "{U}{B}{R}");
        this.expansionSetCode = "LEG";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Archer");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Tetsuo Umezawa can't be the target of Aura spells.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new TetsuoUmezawaEffect()));
        // {U}{B}{B}{R}, {tap}: Destroy target tapped or blocking creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new ManaCostsImpl("{U}{B}{B}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(creatureFilter));
        this.addAbility(ability);
    }

    public TetsuoUmezawa(final TetsuoUmezawa card) {
        super(card);
    }

    @Override
    public TetsuoUmezawa copy() {
        return new TetsuoUmezawa(this);
    }
}

class TetsuoUmezawaEffect extends ContinuousRuleModifiyingEffectImpl {

    public TetsuoUmezawaEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        staticText = "{this} can't be the target of Aura spells";
    }

    public TetsuoUmezawaEffect(final TetsuoUmezawaEffect effect) {
        super(effect);
    }

    @Override
    public TetsuoUmezawaEffect copy() {
        return new TetsuoUmezawaEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGET;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null) {
            return sourcePermanent.getLogName() + " can't be the target of Aura spells";
        }
        return null;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        StackObject stackObject = (StackObject) game.getStack().getStackObject(event.getSourceId());
        if (stackObject != null && event.getTargetId().equals(source.getSourceId())) {
            if (stackObject.getSubtype().contains("Aura")) {
                return true;
            }
        }
        return false;
    }
}
