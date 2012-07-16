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
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SkipNextPlayerUntapStepEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.Targets;

/**
 *
 * @author LevelX
 */
public class YoseiTheMorningStar extends CardImpl<YoseiTheMorningStar> {

    public YoseiTheMorningStar(UUID ownerId) {
        super(ownerId, 50, "Yosei, the Morning Star", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");
        this.expansionSetCode = "CHK";
        this.subtype.add("Dragon");
        this.subtype.add("Spirit");
        this.color.setWhite(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Yosei, the Morning Star dies, target player skips his or her next untap step. Tap up to five target permanents that player controls.
        Ability ability = new DiesTriggeredAbility(new SkipNextPlayerUntapStepEffect());
        ability.addTarget(new TargetPlayer());
        ability.addTarget(new YoseiTheMorningStarTarget());
        ability.addEffect(new YoseiTheMorningStarTapEffect());
        this.addAbility(ability);
    }

    public YoseiTheMorningStar(final YoseiTheMorningStar card) {
        super(card);
    }

    @Override
    public YoseiTheMorningStar copy() {
        return new YoseiTheMorningStar(this);
    }
}

 class YoseiTheMorningStarTarget extends TargetPermanent {

     private static final FilterPermanent filterTemplate = new FilterPermanent("up to five target permanents that player controls that will be tapped");

    public YoseiTheMorningStarTarget() {
        super(0, 5, filterTemplate, false);
    }

    public YoseiTheMorningStarTarget(final YoseiTheMorningStarTarget target) {
        super(target);
    }

        @Override
        public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
                Player player = game.getPlayer(source.getFirstTarget());
                if (player != null) {
                    this.filter = filterTemplate.copy();
                    this.filter.add(new ControllerIdPredicate(player.getId()));
                    return super.canTarget(controllerId, id, source, game);
                }
        return false;
    }

    @Override
    public YoseiTheMorningStarTarget copy() {
        return new YoseiTheMorningStarTarget(this);
    }

}

class YoseiTheMorningStarTapEffect extends OneShotEffect<YoseiTheMorningStarTapEffect> {

    public YoseiTheMorningStarTapEffect() {
        super(Outcome.Tap);
                staticText = "Tap up to five target permanents that player controls";
    }

    public YoseiTheMorningStarTapEffect(final YoseiTheMorningStarTapEffect effect) {
        super(effect);
    }

    @Override
    public YoseiTheMorningStarTapEffect copy() {
        return new YoseiTheMorningStarTapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
                Targets targets = source.getTargets();
                Target target1 = targets.get(1);
                for (UUID target: target1.getTargets()) {
            Permanent permanent = game.getPermanent(target);
            if (permanent != null) {
                permanent.tap(game);
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
            return staticText;
    }

}
