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
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author L_J
 */
public class Halfdane extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("target creature other than Halfdane");

    static {
        filter.add(new AnotherPredicate());
    }

    public Halfdane(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of your upkeep, change Halfdane's base power and toughness to the power and toughness of target creature other than Halfdane until the end of your next upkeep.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new HalfdaneUpkeepEffect(), TargetController.YOU, false);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    public Halfdane(final Halfdane card) {
        super(card);
    }

    @Override
    public Halfdane copy() {
        return new Halfdane(this);
    }
}

class HalfdaneUpkeepEffect extends OneShotEffect {

    public HalfdaneUpkeepEffect() {
        super(Outcome.Detriment);
        this.staticText = "change {this}'s base power and toughness to the power and toughness of target creature other than Halfdane until the end of your next upkeep";
    }

    public HalfdaneUpkeepEffect(final HalfdaneUpkeepEffect effect) {
        super(effect);
    }

    @Override
    public HalfdaneUpkeepEffect copy() {
        return new HalfdaneUpkeepEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
            if (permanent != null) {
                ContinuousEffect effect = new HalfdaneSetPowerToughnessEffect(permanent.getPower().getValue(), permanent.getToughness().getValue());
                game.addEffect(effect, source);
                return true;
            }
        }
        return false;
    }
}

class HalfdaneSetPowerToughnessEffect extends SetPowerToughnessSourceEffect {

    public HalfdaneSetPowerToughnessEffect(int power, int toughness) {
        super(power, toughness, Duration.UntilYourNextTurn, SubLayer.SetPT_7b);
    }

    public HalfdaneSetPowerToughnessEffect(final HalfdaneSetPowerToughnessEffect effect) {
        super(effect);
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        if (super.isInactive(source, game) && game.getStep().getType().isAfter(PhaseStep.UPKEEP)) {
            return true;
        }
        return false;
    }

    @Override
    public HalfdaneSetPowerToughnessEffect copy() {
        return new HalfdaneSetPowerToughnessEffect(this);
    }

}
