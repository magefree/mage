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
package mage.sets.championsofkamigawa;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtEndOfTurnDelayedTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.sets.tokens.EmptyToken;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author jonubuu
 */
public class KikiJikiMirrorBreaker extends CardImpl<KikiJikiMirrorBreaker> {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("nonlegendary creature you control");

    static {
        filter.add(Predicates.not(new SubtypePredicate("Legendary")));
    }

    public KikiJikiMirrorBreaker(UUID ownerId) {
        super(ownerId, 175, "Kiki-Jiki, Mirror Breaker", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{R}{R}{R}");
        this.expansionSetCode = "CHK";
        this.supertype.add("Legendary");
        this.subtype.add("Goblin");
        this.subtype.add("Shaman");

        this.color.setRed(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // {tap}: Put a token that's a copy of target nonlegendary creature you control onto the battlefield. That token has haste. Sacrifice it at the beginning of the next end step.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new KikiJikiMirrorBreakerEffect(), new TapSourceCost());
        ability.addTarget(new TargetControlledCreaturePermanent(1, 1, filter, false));
        this.addAbility(ability);

    }

    public KikiJikiMirrorBreaker(final KikiJikiMirrorBreaker card) {
        super(card);
    }

    @Override
    public KikiJikiMirrorBreaker copy() {
        return new KikiJikiMirrorBreaker(this);
    }
}

class KikiJikiMirrorBreakerEffect extends OneShotEffect<KikiJikiMirrorBreakerEffect> {

    public KikiJikiMirrorBreakerEffect() {
        super(Constants.Outcome.PutCreatureInPlay);
        this.staticText = "Put a token that's a copy of target nonlegendary creature you control onto the battlefield. That token has haste. Sacrifice it at the beginning of the next end step";
    }

    public KikiJikiMirrorBreakerEffect(final KikiJikiMirrorBreakerEffect effect) {
        super(effect);
    }

    @Override
    public KikiJikiMirrorBreakerEffect copy() {
        return new KikiJikiMirrorBreakerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            permanent = (Permanent) game.getLastKnownInformation(source.getFirstTarget(), Zone.BATTLEFIELD);
        }

        if (permanent != null) {
            EmptyToken token = new EmptyToken();
            CardUtil.copyTo(token).from(permanent);

            token.addAbility(HasteAbility.getInstance());
            token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());

            ExileTargetEffect exileEffect = new ExileTargetEffect();
            exileEffect.setTargetPointer(new FixedTarget(token.getLastAddedToken()));
            DelayedTriggeredAbility delayedAbility = new AtEndOfTurnDelayedTriggeredAbility(exileEffect);
            delayedAbility.setSourceId(source.getSourceId());
            delayedAbility.setControllerId(source.getControllerId());
            game.addDelayedTriggeredAbility(delayedAbility);
            return true;
        }

        return false;
    }
}
