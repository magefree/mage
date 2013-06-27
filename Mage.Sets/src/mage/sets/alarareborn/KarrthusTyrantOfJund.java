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
package mage.sets.alarareborn;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.GainAbilityAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class KarrthusTyrantOfJund extends CardImpl<KarrthusTyrantOfJund> {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("all dragons you control");
    
    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new SubtypePredicate("Dragon"));
    }

    public KarrthusTyrantOfJund(UUID ownerId) {
        super(ownerId, 117, "Karrthus, Tyrant of Jund", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{4}{B}{R}{G}");
        this.expansionSetCode = "ARB";
        this.supertype.add("Legendary");
        this.subtype.add("Dragon");

        this.color.setRed(true);
        this.color.setGreen(true);
        this.color.setBlack(true);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Haste
        this.addAbility(HasteAbility.getInstance());
        
        // When Karrthus, Tyrant of Jund enters the battlefield, gain control of all Dragons, then untap all Dragons.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new KarrthusEffect()));
        
        // Other Dragon creatures you control have haste.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(HasteAbility.getInstance(), Duration.WhileOnBattlefield, filter, true)));
        
    }

    public KarrthusTyrantOfJund(final KarrthusTyrantOfJund card) {
        super(card);
    }

    @Override
    public KarrthusTyrantOfJund copy() {
        return new KarrthusTyrantOfJund(this);
    }
}

class KarrthusEffect extends OneShotEffect<KarrthusEffect> {

    public KarrthusEffect() {
        super(Outcome.GainControl);
        this.staticText = "gain control of all dragons";
    }

    public KarrthusEffect(final KarrthusEffect effect) {
        super(effect);
    }

    @Override
    public KarrthusEffect copy() {
        return new KarrthusEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterPermanent filter = new FilterPermanent();
        filter.add(new SubtypePredicate("Dragon"));
        List<Permanent> dragons = game.getBattlefield().getAllActivePermanents(filter, game);
        for (Permanent dragon : dragons) {
            ContinuousEffect effect = new KarrthusControlEffect(source.getControllerId());
            effect.setTargetPointer(new FixedTarget(dragon.getId()));
            game.addEffect(effect, source);
        }
        for (Permanent dragon : dragons) {
            dragon.untap(game);
        }
        return true;
    }
}

class KarrthusControlEffect extends ContinuousEffectImpl<KarrthusControlEffect> {

    private UUID controllerId;

    public KarrthusControlEffect(UUID controllerId) {
        super(Duration.WhileOnBattlefield, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        this.controllerId = controllerId;
    }

    public KarrthusControlEffect(final KarrthusControlEffect effect) {
        super(effect);
        this.controllerId = effect.controllerId;
    }

    @Override
    public KarrthusControlEffect copy() {
        return new KarrthusControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent dragon = game.getPermanent(targetPointer.getFirst(game, source));
        if (dragon != null && controllerId != null) {
            return dragon.changeControllerId(controllerId, game);
        }
        return false;
    }
}
