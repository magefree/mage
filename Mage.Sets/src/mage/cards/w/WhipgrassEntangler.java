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
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.combat.CantAttackBlockUnlessPaysSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000 & L_J
 */
public class WhipgrassEntangler extends CardImpl {

    public WhipgrassEntangler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {1}{W}: Until end of turn, target creature gains "This creature can't attack or block unless its controller pays {1} for each Cleric on the battlefield."
        Ability abilityToGain = new SimpleStaticAbility(Zone.BATTLEFIELD, new WhipgrassEntanglerCantAttackUnlessYouPayEffect());
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, 
                new GainAbilityTargetEffect(abilityToGain, Duration.EndOfTurn).setText("Until end of turn, target creature gains \"This creature can't attack or block unless its controller pays {1} for each Cleric on the battlefield.\""), 
                new ManaCostsImpl("{1}{W}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

    }

    public WhipgrassEntangler(final WhipgrassEntangler card) {
        super(card);
    }

    @Override
    public WhipgrassEntangler copy() {
        return new WhipgrassEntangler(this);
    }
}

class WhipgrassEntanglerCantAttackUnlessYouPayEffect extends CantAttackBlockUnlessPaysSourceEffect {

    private static final FilterPermanent filter = new FilterPermanent("Cleric on the battlefield");

    static {
        filter.add(new SubtypePredicate(SubType.CLERIC));
    }

    WhipgrassEntanglerCantAttackUnlessYouPayEffect() {
        super(new ManaCostsImpl("{0}"), RestrictType.ATTACK_AND_BLOCK);
        staticText = "This creature can't attack or block unless its controller pays {1} for each Cleric on the battlefield";
    }

    WhipgrassEntanglerCantAttackUnlessYouPayEffect(WhipgrassEntanglerCantAttackUnlessYouPayEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.getSourceId().equals(event.getSourceId());
    }

    @Override
    public ManaCosts getManaCostToPay(GameEvent event, Ability source, Game game) {
        Permanent sourceObject = game.getPermanent(source.getSourceId());
        if (sourceObject != null) {
            int payment = new PermanentsOnBattlefieldCount(filter).calculate(game, source, this);
            if (payment > 0) {
                return new ManaCostsImpl<>("{" + payment + '}');
            }
        }
        return null;
    }

    @Override
    public WhipgrassEntanglerCantAttackUnlessYouPayEffect copy() {
        return new WhipgrassEntanglerCantAttackUnlessYouPayEffect(this);
    }

}
