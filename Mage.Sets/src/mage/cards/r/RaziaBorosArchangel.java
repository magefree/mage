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
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.RedirectionEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public class RaziaBorosArchangel extends CardImpl {

    public RaziaBorosArchangel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}{R}{W}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Angel");

        this.power = new MageInt(6);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {T}: The next 3 damage that would be dealt to target creature you control this turn is dealt to another target creature instead.
        Effect effect = new RaziaBorosArchangelEffect(Duration.EndOfTurn, 3);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new TapSourceCost());
        Target target = new TargetControlledCreaturePermanent();
        target.setTargetTag(1);
        ability.addTarget(target);

        FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature (damage is redirected to)");
        filter.add(new AnotherTargetPredicate(2));
        target = new TargetCreaturePermanent(filter);
        target.setTargetTag(2);
        ability.addTarget(target);
        this.addAbility(ability);

    }

    public RaziaBorosArchangel(final RaziaBorosArchangel card) {
        super(card);
    }

    @Override
    public RaziaBorosArchangel copy() {
        return new RaziaBorosArchangel(this);
    }
}

class RaziaBorosArchangelEffect extends RedirectionEffect {

    protected MageObjectReference redirectToObject;

    public RaziaBorosArchangelEffect(Duration duration, int amount) {
        super(duration, 3, true);
        staticText = "The next " + amount + " damage that would be dealt to target creature you control this turn is dealt to another target creature instead";
    }

    public RaziaBorosArchangelEffect(final RaziaBorosArchangelEffect effect) {
        super(effect);
    }

    @Override
    public RaziaBorosArchangelEffect copy() {
        return new RaziaBorosArchangelEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        redirectToObject = new MageObjectReference(source.getTargets().get(1).getFirstTarget(), game);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(getTargetPointer().getFirst(game, source))) {
            if (game.getControllerId(redirectToObject.getSourceId()) != null) {
                if (redirectToObject.equals(new MageObjectReference(source.getTargets().get(1).getFirstTarget(), game))) {
                    redirectTarget = source.getTargets().get(1);
                    return true;
                }
            }
        }
        return false;
    }
}
