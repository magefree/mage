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
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author jeffwadsworth
 */
public class ScourgeOfValkas extends CardImpl {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent("{this} or another Dragon");

    static {
        filter.add(new SubtypePredicate(SubType.DRAGON));
    }

    private static final String rule = "Whenever {this} or another Dragon enters the battlefield under your control, it deals X damage to any target, where X is the number of Dragons you control.";

    public ScourgeOfValkas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}{R}");
        this.subtype.add(SubType.DRAGON);

        this.color.setRed(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Scourge of Valkas or another Dragon enters the battlefield under your control, it deals X damage to any target, where X is the number of Dragons you control.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, new ScourgeOfValkasDamageEffect(), filter, false, rule);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // {R}: Scourge of Valkas gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl("{R}")));

    }

    public ScourgeOfValkas(final ScourgeOfValkas card) {
        super(card);
    }

    @Override
    public ScourgeOfValkas copy() {
        return new ScourgeOfValkas(this);
    }
}

class ScourgeOfValkasDamageEffect extends OneShotEffect {

    public ScourgeOfValkasDamageEffect() {
        super(Outcome.Damage);
        this.staticText = "it deals X damage to any target, where X is the number of Dragons you control";
    }

    public ScourgeOfValkasDamageEffect(final ScourgeOfValkasDamageEffect effect) {
        super(effect);
    }

    @Override
    public ScourgeOfValkasDamageEffect copy() {
        return new ScourgeOfValkasDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent enteringDragon = (Permanent) getValue("permanentEnteringBattlefield");
        if (controller != null && enteringDragon != null) {
            FilterPermanent filter = new FilterPermanent();
            filter.add(new SubtypePredicate(SubType.DRAGON));
            int dragons = game.getBattlefield().countAll(filter, source.getControllerId(), game);
            if (dragons > 0) {
                Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
                if (permanent != null) {
                    permanent.damage(dragons, enteringDragon.getId(), game, false, true);
                } else {
                    Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
                    if (player != null
                            && player.isInGame()) {
                        player.damage(dragons, enteringDragon.getId(), game, false, true);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
