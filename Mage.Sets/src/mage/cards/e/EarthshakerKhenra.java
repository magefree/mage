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
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.keyword.EternalizeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class EarthshakerKhenra extends CardImpl {

    private final UUID originalId;
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with power less than or equal to {this}'s power");

    public EarthshakerKhenra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add("Jackal");
        this.subtype.add("Warrior");
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Earthshaker Khenra enters the battlefield, target creature with power less than or equal to Earthshaker Khenra's power can't block this turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new EarthshakerKhenraEffect());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
        originalId = ability.getOriginalId();

        // Eternalize {4}{R}{R}
        this.addAbility(new EternalizeAbility(new ManaCostsImpl("{4}{R}{R}"), this));

    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability.getOriginalId().equals(originalId)) {
            Permanent sourcePermanent = game.getPermanent(ability.getSourceId());
            if (sourcePermanent != null) {
                FilterCreaturePermanent targetFilter = new FilterCreaturePermanent("creature with power less than or equal to " + getLogName() + "'s power");
                targetFilter.add(new PowerPredicate(ComparisonType.FEWER_THAN, sourcePermanent.getPower().getValue() + 1));
                ability.getTargets().clear();
                ability.getTargets().add(new TargetCreaturePermanent(targetFilter));
            }
        }
    }

    public EarthshakerKhenra(final EarthshakerKhenra card) {
        super(card);
        this.originalId = card.originalId;
    }

    @Override
    public EarthshakerKhenra copy() {
        return new EarthshakerKhenra(this);
    }
}

class EarthshakerKhenraEffect extends OneShotEffect {

    public EarthshakerKhenraEffect() {
        super(Outcome.UnboostCreature);
        this.staticText = "target creature with power less than or equal to {this}'s power can't block this turn";
    }

    public EarthshakerKhenraEffect(final EarthshakerKhenraEffect effect) {
        super(effect);
    }

    @Override
    public EarthshakerKhenraEffect copy() {
        return new EarthshakerKhenraEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourceObject = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourceObject != null) {
            Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
            /*
            27.06.2017 	The target creature’s power is checked when you target it with Earthshaker Khenra’s ability
                        and when that ability resolves. Once the ability resolves, if the creature’s power increases
                        or Earthshaker Khenra’s power decreases, the target creature will still be unable to block.
             */
            if (targetCreature != null && targetCreature.getPower().getValue() <= sourceObject.getPower().getValue()) {
                ContinuousEffect effect = new CantBlockTargetEffect(Duration.EndOfTurn);
                effect.setTargetPointer(new FixedTarget(targetCreature, game));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }
}
