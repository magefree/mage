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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public class MossbridgeTroll extends CardImpl {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped creatures you control");

    static {
        filter.add(Predicates.not(new TappedPredicate()));
    }

    public MossbridgeTroll(UUID ownerId) {
        super(ownerId, 123, "Mossbridge Troll", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");
        this.expansionSetCode = "SHM";
        this.subtype.add("Troll");

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // If Mossbridge Troll would be destroyed, regenerate it.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new MossbridgeTrollReplacementEffect()));

        // Tap any number of untapped creatures you control other than Mossbridge Troll with total power 10 or greater: Mossbridge Troll gets +20/+20 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(20, 20, Duration.EndOfTurn), new MossbridgeTrollCost());
        ability.setAdditionalCostsRuleVisible(false);
        this.addAbility(ability);
               
    }

    public MossbridgeTroll(final MossbridgeTroll card) {
        super(card);
    }

    @Override
    public MossbridgeTroll copy() {
        return new MossbridgeTroll(this);
    }
}

class MossbridgeTrollReplacementEffect extends ReplacementEffectImpl {

    MossbridgeTrollReplacementEffect() {
        super(Duration.Custom, Outcome.Regenerate);
        staticText = "If {this} would be destroyed, regenerate it";
    }

    MossbridgeTrollReplacementEffect(MossbridgeTrollReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent mossbridgeTroll = game.getPermanent(event.getTargetId());
        if (mossbridgeTroll != null) {
            return mossbridgeTroll.regenerate(source.getSourceId(), game);
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DESTROY_PERMANENT;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId() == source.getSourceId();
    }

    @Override
    public MossbridgeTrollReplacementEffect copy() {
        return new MossbridgeTrollReplacementEffect(this);
    }

}

class MossbridgeTrollCost extends CostImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("any number of untapped creatures other than {this} with total power 10 or greater");

    static {
        filter.add(new AnotherPredicate());
        filter.add(Predicates.not(new TappedPredicate()));
    }

    public MossbridgeTrollCost() {
        this.addTarget(new TargetControlledCreaturePermanent(0, Integer.MAX_VALUE, filter, true));
        this.text = "tap any number of untapped creatures you control other than {this} with total power 10 or greater";
    }

    public MossbridgeTrollCost(final MossbridgeTrollCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        int sumPower = 0;
        if (targets.choose(Outcome.Tap, controllerId, sourceId, game)) {
            for (UUID targetId: targets.get(0).getTargets()) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent != null && permanent.tap(game)) {
                    sumPower += permanent.getPower().getValue();
                }
            }
        }
        game.informPlayers(new StringBuilder("Tap creatures with total power of ").append(sumPower).toString());
        paid = sumPower >= 10;
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        int sumPower = 0;
        for (Permanent permanent :game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), controllerId, game)) {
            if (!permanent.getId().equals(sourceId)) {
                sumPower += permanent.getPower().getValue();
            }
        }
        return sumPower >= 10;
    }

    @Override
    public MossbridgeTrollCost copy() {
        return new MossbridgeTrollCost(this);
    }
}

