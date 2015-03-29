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
package mage.sets.fatereforged;

import java.util.Iterator;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SetTargetPointer;
import mage.constants.SubLayer;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class SilumgarTheDriftingDeath extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Dragon you control");

    static {
        filter.add(new SubtypePredicate("Dragon"));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public SilumgarTheDriftingDeath(UUID ownerId) {
        super(ownerId, 157, "Silumgar, the Drifting Death", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{U}{B}");
        this.expansionSetCode = "FRF";
        this.supertype.add("Legendary");
        this.subtype.add("Dragon");
        this.power = new MageInt(3);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Hexproof
        this.addAbility(HexproofAbility.getInstance());
        // Whenever a Dragon you control attacks, creatures defending player controls get -1/-1 until end of turn.
        this.addAbility(
                new AttacksAllTriggeredAbility(
                    new UnboostCreaturesTargetPlayerEffect(-1, -1),
                    false, filter, SetTargetPointer.PLAYER, false));
    }

    public SilumgarTheDriftingDeath(final SilumgarTheDriftingDeath card) {
        super(card);
    }

    @Override
    public SilumgarTheDriftingDeath copy() {
        return new SilumgarTheDriftingDeath(this);
    }
}


class UnboostCreaturesTargetPlayerEffect extends ContinuousEffectImpl {

    private final int power;
    private final int toughness;

    public UnboostCreaturesTargetPlayerEffect(int power, int toughness) {
        super(Duration.EndOfTurn, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        this.power = power;
        this.toughness = toughness;
        staticText = "creatures defending player controls get -1/-1 until end of turn";
    }

    public UnboostCreaturesTargetPlayerEffect(final UnboostCreaturesTargetPlayerEffect effect) {
        super(effect);
        this.power = effect.power;
        this.toughness = effect.toughness;
    }

    @Override
    public UnboostCreaturesTargetPlayerEffect copy() {
        return new UnboostCreaturesTargetPlayerEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (this.affectedObjectsSet) {
            for (Permanent creature : game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), getTargetPointer().getFirst(game, source), game)) {
                affectedObjectList.add(new MageObjectReference(creature, game));
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext();) {
            Permanent permanent = it.next().getPermanent(game);
            if (permanent != null) {
                permanent.addPower(power);
                permanent.addToughness(toughness);
            } else {
                it.remove();
            }
        }
        return true;
    }
}