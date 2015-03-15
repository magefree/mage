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
package mage.sets.fatereforged;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.TurnedFaceUpAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ManifestEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class TemurWarShaman extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you don't control");

    static {
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    public TemurWarShaman(UUID ownerId) {
        super(ownerId, 142, "Temur War Shaman", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        this.expansionSetCode = "FRF";
        this.subtype.add("Human");
        this.subtype.add("Shaman");
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // When Temur War Shaman enters the battlefield, manifest the top card of your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ManifestEffect(1), false));

        // Whenever a permanent you control is turned face up, if it is a creature, you may have it fight target creature you don't control.
        Ability ability = new TemurWarShamanTriggeredAbility();
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    public TemurWarShaman(final TemurWarShaman card) {
        super(card);
    }

    @Override
    public TemurWarShaman copy() {
        return new TemurWarShaman(this);
    }
}

class TemurWarShamanTriggeredAbility extends TurnedFaceUpAllTriggeredAbility {

    public TemurWarShamanTriggeredAbility() {
        super(new TemurWarShamanFightEffect(), new FilterControlledCreaturePermanent(), true);
    }

    public TemurWarShamanTriggeredAbility(final TemurWarShamanTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TemurWarShamanTriggeredAbility copy() {
        return new TemurWarShamanTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a permanent you control is turned face up, if it is a creature, you may have it fight target creature you don't control.";
    }
}

class TemurWarShamanFightEffect extends OneShotEffect {

    TemurWarShamanFightEffect() {
        super(Outcome.Damage);
    }

    TemurWarShamanFightEffect(final TemurWarShamanFightEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent triggeredCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (triggeredCreature != null
                && target != null
                && triggeredCreature.getCardType().contains(CardType.CREATURE)
                && target.getCardType().contains(CardType.CREATURE)) {
            triggeredCreature.fight(target, source, game);
            return true;
        }
        return false;
    }

    @Override
    public TemurWarShamanFightEffect copy() {
        return new TemurWarShamanFightEffect(this);
    }
}
