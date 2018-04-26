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
package mage.cards.p;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.m.MetzaliTowerOfTriumph;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author LevelX2
 */
public class PathOfMettle extends CardImpl {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent("creature that doesn't have first strike, double strike, vigilance, or haste");

    static {
        filter.add(Predicates.not(Predicates.or(
                new AbilityPredicate(FirstStrikeAbility.class),
                new AbilityPredicate(DoubleStrikeAbility.class),
                new AbilityPredicate(VigilanceAbility.class),
                new AbilityPredicate(HasteAbility.class)
        )));
    }

    public PathOfMettle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}{W}");

        this.addSuperType(SuperType.LEGENDARY);

        this.transformable = true;
        this.secondSideCardClazz = MetzaliTowerOfTriumph.class;

        // When Path of Mettle enters the battlefield, it deals 1 damage to each creature that doesn't have first strike, double strike, vigilance, or haste.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DamageAllEffect(1, filter)));

        // Whenever you attack with at least two creatures that have first strike, double strike, vigilance, and/or haste, transform Path of Mettle.
        this.addAbility(new TransformAbility());
        this.addAbility(new PathOfMettleTriggeredAbility());
    }

    public PathOfMettle(final PathOfMettle card) {
        super(card);
    }

    @Override
    public PathOfMettle copy() {
        return new PathOfMettle(this);
    }
}

class PathOfMettleTriggeredAbility extends TriggeredAbilityImpl {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent("creature that doesn't have first strike, double strike, vigilance, or haste");

    static {
        filter.add(Predicates.or(
                new AbilityPredicate(FirstStrikeAbility.class),
                new AbilityPredicate(DoubleStrikeAbility.class),
                new AbilityPredicate(VigilanceAbility.class),
                new AbilityPredicate(HasteAbility.class)
        ));
    }

    public PathOfMettleTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TransformSourceEffect(true));
    }

    public PathOfMettleTriggeredAbility(final PathOfMettleTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PathOfMettleTriggeredAbility copy() {
        return new PathOfMettleTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        int attackerCount = 0;
        if (game.getCombat() != null) {
            if (getControllerId().equals(game.getCombat().getAttackingPlayerId())) {
                for (UUID attacker : game.getCombat().getAttackers()) {
                    if (filter.match(game.getPermanent(attacker), game)) {
                        attackerCount++;
                    }
                }
                return attackerCount >= 2;
            }
        }
        return false;

    }

    @Override
    public String getRule() {
        return "Whenever you attack with at least two creatures that have first strike, double strike, vigilance, and/or haste, transform Path of Mettle";
    }

}
