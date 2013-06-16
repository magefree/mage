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
package mage.sets.dragonsmaze;

import java.util.UUID;

import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class ScabClanGiant extends CardImpl<ScabClanGiant> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");
    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public ScabClanGiant(UUID ownerId) {
        super(ownerId, 101, "Scab-Clan Giant", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{4}{R}{G}");
        this.expansionSetCode = "DGM";
        this.subtype.add("Giant");
        this.subtype.add("Warrior");

        this.color.setRed(true);
        this.color.setGreen(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // When Scab-Clan Giant enters the battlefield, it fights target creature an opponent controls chosen at random.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ScabClanGiantEffect());
        Target target = new TargetCreaturePermanent(filter);
        target.setRandom(true);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    public ScabClanGiant(final ScabClanGiant card) {
        super(card);
    }

    @Override
    public ScabClanGiant copy() {
        return new ScabClanGiant(this);
    }
}

class ScabClanGiantEffect extends OneShotEffect<ScabClanGiantEffect> {

    public ScabClanGiantEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} fights target creature an opponent controls chosen at random";
    }

    public ScabClanGiantEffect(final ScabClanGiantEffect effect) {
        super(effect);
    }

    @Override
    public ScabClanGiantEffect copy() {
        return new ScabClanGiantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature1 = game.getPermanent(source.getSourceId());
        Permanent creature2 = game.getPermanent(source.getFirstTarget());
        // 20110930 - 701.10
        if (creature1 != null && creature2 != null) {
            if (creature1.getCardType().contains(CardType.CREATURE) && creature2.getCardType().contains(CardType.CREATURE)) {
                creature1.damage(creature2.getPower().getValue(), creature2.getId(), game, true, false);
                creature2.damage(creature1.getPower().getValue(), creature1.getId(), game, true, false);
                return true;
            }
        }
        return false;
    }
}