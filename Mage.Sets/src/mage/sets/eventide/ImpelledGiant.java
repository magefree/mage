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
package mage.sets.eventide;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class ImpelledGiant extends CardImpl<ImpelledGiant> {

    static final private FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("an untapped red creature you control other than Impelled Giant");

    static {
        filter.add(Predicates.not(new TappedPredicate()));
        filter.add(new ColorPredicate(ObjectColor.RED));
        filter.add(new AnotherPredicate());
    }

    public ImpelledGiant(UUID ownerId) {
        super(ownerId, 58, "Impelled Giant", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");
        this.expansionSetCode = "EVE";
        this.subtype.add("Giant");
        this.subtype.add("Warrior");

        this.color.setRed(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Tap an untapped red creature you control other than Impelled Giant: Impelled Giant gets +X/+0 until end of turn, where X is the power of the creature tapped this way.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ImpelledGiantBoostEffect(), new ImpelledGiantCost(new TargetControlledPermanent(filter))));

    }

    public ImpelledGiant(final ImpelledGiant card) {
        super(card);
    }

    @Override
    public ImpelledGiant copy() {
        return new ImpelledGiant(this);
    }
}

class ImpelledGiantCost extends CostImpl<ImpelledGiantCost> {

    TargetControlledPermanent target;

    public ImpelledGiantCost(TargetControlledPermanent target) {
        this.target = target;
        this.text = "Tap an untapped red creature you control other than Impelled Giant";
    }

    public ImpelledGiantCost(final ImpelledGiantCost cost) {
        super(cost);
        this.target = cost.target.copy();
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        if (target.choose(Outcome.Tap, controllerId, sourceId, game)) {
            for (UUID targetId: (List<UUID>)target.getTargets()) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent == null)
                    return false;
                paid |= permanent.tap(game);
                for (Effect effect : ability.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(permanent.getId()));
                }
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
        return target.canChoose(controllerId, game);
    }

    @Override
    public ImpelledGiantCost copy() {
        return new ImpelledGiantCost(this);
    }


}


class ImpelledGiantBoostEffect extends OneShotEffect<ImpelledGiantBoostEffect> {

    public ImpelledGiantBoostEffect() {
        super(Outcome.BoostCreature);
        staticText = "{this} gets +X/+0 until end of turn, where X is the power of the creature tapped this way";
    }

    public ImpelledGiantBoostEffect(ImpelledGiantBoostEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent impelledGiant = game.getPermanent(source.getSourceId());
        Permanent tappedCreature = game.getPermanent(this.targetPointer.getFirst(game, source));
        if (tappedCreature == null) {
            tappedCreature = (Permanent) game.getLastKnownInformation(this.targetPointer.getFirst(game, source), Zone.BATTLEFIELD);
        }
        if (tappedCreature != null && impelledGiant != null) {
            int amount = tappedCreature.getPower().getValue();
            impelledGiant.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(amount, 0, Duration.EndOfTurn)), source.getSourceId(), game);
        }
        return true;
    }

    @Override
    public ImpelledGiantBoostEffect copy() {
        return new ImpelledGiantBoostEffect(this);
    }
}
