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
package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.NoSpellsWereCastLastTurnCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public class LambholtPacifist extends CardImpl {

    public LambholtPacifist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add("Human");
        this.subtype.add("Shaman");
        this.subtype.add("Werewolf");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.transformable = true;
        this.secondSideCardClazz = LambholtButcher.class;

        // Lambholt Pacifist can't attack unless you control a creature with power 4 or greater.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LambholtPacifistEffect()));

        // At the beginning of each upkeep, if no spells were cast last turn, transform Lambholt Pacifist.
        this.addAbility(new TransformAbility());
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(true), TargetController.ANY, false);
        this.addAbility(new ConditionalTriggeredAbility(ability, NoSpellsWereCastLastTurnCondition.instance, TransformAbility.NO_SPELLS_TRANSFORM_RULE));
    }

    public LambholtPacifist(final LambholtPacifist card) {
        super(card);
    }

    @Override
    public LambholtPacifist copy() {
        return new LambholtPacifist(this);
    }
}

class LambholtPacifistEffect extends RestrictionEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a creature with power 4 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    public LambholtPacifistEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack unless you control a creature with power 4 or greater";
    }

    public LambholtPacifistEffect(final LambholtPacifistEffect effect) {
        super(effect);
    }

    @Override
    public LambholtPacifistEffect copy() {
        return new LambholtPacifistEffect(this);
    }

    @Override
    public boolean canAttack(Game game) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId())) {
            if (game.getBattlefield().countAll(filter, source.getControllerId(), game) > 0) {
                return false;
            }
            return true;
        }  // do not apply to other creatures.
        return false;
    }
}

