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
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.RedirectionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801 & L_J
 */
public class ShimianNightStalker extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("attacking creature");

    static {
        filter.add(new AttackingPredicate());
    }

    public ShimianNightStalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.NIGHTSTALKER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {B}, {T}: All damage that would be dealt to you this turn by target attacking creature is dealt to Shimian Night Stalker instead.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ShimianNightStalkerRedirectDamageEffect(), new ManaCostsImpl("{B}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    public ShimianNightStalker(final ShimianNightStalker card) {
        super(card);
    }

    @Override
    public ShimianNightStalker copy() {
        return new ShimianNightStalker(this);
    }
}

class ShimianNightStalkerRedirectDamageEffect extends RedirectionEffect {

    private static FilterCreaturePermanent filter = new FilterCreaturePermanent();

    public ShimianNightStalkerRedirectDamageEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, true);
        this.staticText = "All damage that would be dealt to you this turn by target attacking creature is dealt to {this} instead";
    }

    public ShimianNightStalkerRedirectDamageEffect(final ShimianNightStalkerRedirectDamageEffect effect) {
        super(effect);
    }

    @Override
    public ShimianNightStalkerRedirectDamageEffect copy() {
        return new ShimianNightStalkerRedirectDamageEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getBattlefield().getPermanent(source.getSourceId());
        if (permanent != null) {
            if (filter.match(permanent, permanent.getId(), permanent.getControllerId(), game)) {
                if (event.getSourceId() != null && event.getTargetId() != null) {
                    if (event.getSourceId().equals(getTargetPointer().getFirst(game, source)) 
                            && event.getTargetId().equals(source.getControllerId())) {
                        TargetPermanent target = new TargetPermanent();
                        target.add(source.getSourceId(), game);
                        redirectTarget = target;
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
