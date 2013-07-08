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
package mage.sets.magic2014;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTargetTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class DismissIntoDream extends CardImpl<DismissIntoDream> {

    public DismissIntoDream(UUID ownerId) {
        super(ownerId, 50, "Dismiss into Dream", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{6}{U}");
        this.expansionSetCode = "M14";

        this.color.setBlue(true);

        // Each creature your opponents control is an Illusion in addition to its other types 
        // and has "When this creature becomes the target of a spell or ability, sacrifice it."
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DismissIntoDreamEffect()));
    }

    public DismissIntoDream(final DismissIntoDream card) {
        super(card);
    }

    @Override
    public DismissIntoDream copy() {
        return new DismissIntoDream(this);
    }
}

class DismissIntoDreamEffect extends ContinuousEffectImpl<DismissIntoDreamEffect> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();
    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    DismissIntoDreamEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        this.staticText = "Each creature your opponents control is an Illusion in addition to its other types and has \"When this creature becomes the target of a spell or ability, sacrifice it.\"";
    }

    DismissIntoDreamEffect(final DismissIntoDreamEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public DismissIntoDreamEffect copy() {
        return new DismissIntoDreamEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (Permanent object: game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
            switch (layer) {
                case AbilityAddingRemovingEffects_6:
                    object.addAbility(new BecomesTargetTriggeredAbility(new SacrificeSourceEffect()), source.getSourceId(), game);
                    break;
                case TypeChangingEffects_4:
                    if (!object.getSubtype().contains("Illusion")) {
                        object.getSubtype().add("Illusion");
                    }
                    break;
            }
        }
        return true;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.TypeChangingEffects_4;
    }
}
