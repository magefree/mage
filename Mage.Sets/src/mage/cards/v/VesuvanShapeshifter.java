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
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsTurnedFaceUpEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.util.functions.ApplyToPermanent;

/**
 * @author spjspj
 */
public class VesuvanShapeshifter extends CardImpl {

    protected Ability turnFaceUpAbility = null;
    private static final String effectText = "as a copy of any creature on the battlefield until {this} is turned faced down";

    public VesuvanShapeshifter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.subtype.add("Shapeshifter");
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Morph {1}{U}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl("{1}{U}")));

        // As Vesuvan Shapeshifter turned face up, may choose another creature. If you do, until Vesuvan Shapeshifter is turned face down, it becomes a copy of that creature
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new AsTurnedFaceUpEffect(new VesuvanShapeshifterEffect(), false));
        ability.setWorksFaceDown(true);
        this.addAbility(ability);

        // As Vesuvan Shapeshifter etbs, may choose another creature. If you do, until Vesuvan Shapeshifter is turned face down, it becomes a copy of that creature
        Effect effect = new CopyPermanentEffect(new FilterCreaturePermanent(), new VesuvanShapeShifterFaceUpApplier());
        effect.setText(effectText);
        ability = new EntersBattlefieldAbility(effect, true);
        ability.setWorksFaceDown(false);
        this.addAbility(ability);

        // and gains "At the beginning of your upkeep, you may turn this creature face down".
        effect = new VesuvanShapeshifterFaceDownEffect();
        ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, effect, TargetController.YOU, true);
        this.addAbility(ability);
    }

    public VesuvanShapeshifter(final VesuvanShapeshifter card) {
        super(card);
    }

    @Override
    public VesuvanShapeshifter copy() {
        return new VesuvanShapeshifter(this);
    }
}

class VesuvanShapeShifterFaceUpApplier extends ApplyToPermanent {

    @Override
    public boolean apply(Game game, Permanent permanent, Ability source, UUID copyToObjectId) {
        Effect effect = new VesuvanShapeshifterFaceDownEffect();
        Ability ability = new BeginningOfUpkeepTriggeredAbility(effect, TargetController.YOU, true);
        permanent.getAbilities().add(ability);
        permanent.addAbility(new MorphAbility(permanent, new ManaCostsImpl("{1}{U}")), permanent.getId(), game);
        return true;
    }

    @Override
    public boolean apply(Game game, MageObject mageObject, Ability source, UUID copyToObjectId) {
        Effect effect = new VesuvanShapeshifterFaceDownEffect();
        Ability ability = new BeginningOfUpkeepTriggeredAbility(effect, TargetController.YOU, true);
        mageObject.getAbilities().add(ability);
        return true;
    }
}

class VesuvanShapeshifterEffect extends OneShotEffect {

    public VesuvanShapeshifterEffect() {
        super(Outcome.Copy);
        staticText = "have {this} become a copy of a creature and gain this ability";
    }

    public VesuvanShapeshifterEffect(final VesuvanShapeshifterEffect effect) {
        super(effect);
    }

    @Override
    public VesuvanShapeshifterEffect copy() {
        return new VesuvanShapeshifterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());

        Permanent copyToCreature = game.getPermanent(source.getSourceId());
        if (copyToCreature != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature");
            filter.add(new AnotherPredicate());

            TargetCreaturePermanent target = new TargetCreaturePermanent(0, 1, filter, false);

            if (controller.chooseTarget(Outcome.BecomeCreature, target, source, game) && !target.getTargets().isEmpty()) {
                Permanent copyFromCreature = game.getPermanentOrLKIBattlefield(target.getFirstTarget());
                if (copyFromCreature != null) {
                    game.copyPermanent(Duration.Custom, copyFromCreature, copyToCreature.getId(), source, new VesuvanShapeShifterFaceUpApplier());
                    source.getTargets().clear();
                    return true;
                }
            }
        }
        return false;
    }
}

class VesuvanShapeshifterFaceDownEffect extends OneShotEffect {

    public VesuvanShapeshifterFaceDownEffect() {
        super(Outcome.Copy);
        staticText = "have {this} become a morphed, faced down creature";
    }

    public VesuvanShapeshifterFaceDownEffect(final VesuvanShapeshifterFaceDownEffect effect) {
        super(effect);
    }

    @Override
    public VesuvanShapeshifterFaceDownEffect copy() {
        return new VesuvanShapeshifterFaceDownEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());

        Permanent permanent = game.getPermanent(source.getSourceId());
        if (controller != null && permanent != null) {
            permanent.removeAllAbilities(source.getSourceId(), game);

            // Set any previous copy effects to 'discarded'
            for (Effect effect : game.getState().getContinuousEffects().getLayeredEffects(game)) {
                if (effect instanceof CopyEffect) {
                    CopyEffect copyEffect = (CopyEffect) effect;
                    if (copyEffect.getSourceId().equals(permanent.getId())) {
                        copyEffect.discard();
                    }
                }
            }

            permanent.turnFaceDown(game, source.getControllerId());
            permanent.setManifested(false);
            permanent.setMorphed(true);
            return permanent.isFaceDown(game);
        }

        return false;
    }
}
