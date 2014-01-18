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
package mage.sets.theros;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesMonstrousSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanentAmount;

/**
 *
 * * The value of X in Polukranos’s last ability is equal to the value chosen
 *   for X when its activated ability was activated.
 *
 * * The number of targets chosen for the triggered ability must be at least one
 *   (if X wasn’t 0) and at most X. You choose the division of damage as you put
 *   the ability on the stack, not as it resolves. Each target must be assigned
 *   at least 1 damage. In multiplayer games, you may choose creatures controlled
 *   by different opponents.
 *
 * * If some, but not all, of the ability’s targets become illegal, you can’t change
 *   the division of damage. Damage that would’ve been dealt to illegal targets
 *   simply isn’t dealt.
 *
 * * As Polukranos’s triggered ability resolves, Polukranos deals damage first, then
 *   the target creatures do. Although no creature will die until after the ability
 *   finishes resolving, the order could matter if Polukranos has wither or infect.
 *
 * @author LevelX2
 */
public class PolukranosWorldEater extends CardImpl<PolukranosWorldEater> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();
    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public PolukranosWorldEater(UUID ownerId) {
        super(ownerId, 172, "Polukranos, World Eater", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.expansionSetCode = "THS";
        this.supertype.add("Legendary");
        this.subtype.add("Hydra");

        this.color.setGreen(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // {X}{X}{G}: Monstrosity X.
        this.addAbility(new MonstrosityAbility("{X}{X}{G}", Integer.MAX_VALUE));
        // When Polukranos, World Eater becomes monstrous, it deals X damage divided as you choose among any number of target creatures your opponents control. Each of those creatures deals damage equal to its power to Polukranos.
        Ability ability = new BecomesMonstrousSourceTriggeredAbility(new PolukranosWorldEaterEffect());
        ability.addTarget(new TargetCreaturePermanentAmount(1, filter));
        this.addAbility(ability);

    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof BecomesMonstrousSourceTriggeredAbility) {
            int xValue = ((BecomesMonstrousSourceTriggeredAbility) ability).getMonstrosityValue();
            ability.getTargets().clear();
            ability.addTarget(new TargetCreaturePermanentAmount(xValue, filter));
        }
    }

    public PolukranosWorldEater(final PolukranosWorldEater card) {
        super(card);
    }

    @Override
    public PolukranosWorldEater copy() {
        return new PolukranosWorldEater(this);
    }
}

class PolukranosWorldEaterEffect extends OneShotEffect<PolukranosWorldEaterEffect> {

    public PolukranosWorldEaterEffect() {
        super(Outcome.Benefit);
        this.staticText = "it deals X damage divided as you choose among any number of target creatures your opponents control. Each of those creatures deals damage equal to its power to Polukranos";
    }

    public PolukranosWorldEaterEffect(final PolukranosWorldEaterEffect effect) {
        super(effect);
    }

    @Override
    public PolukranosWorldEaterEffect copy() {
        return new PolukranosWorldEaterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getTargets().size() > 0) {
            Target multiTarget = source.getTargets().get(0);
            Set<Permanent> permanents = new HashSet<Permanent>();
            for (UUID target: multiTarget.getTargets()) {
                Permanent permanent = game.getPermanent(target);
                if (permanent != null) {
                    permanents.add(permanent);
                    permanent.damage(multiTarget.getTargetAmount(target), source.getSourceId(), game, true, false);
                }
            }
            // Each of those creatures deals damage equal to its power to Polukranos
            Permanent sourceCreature = game.getPermanent(source.getSourceId());
            if (sourceCreature != null) {
                for (Permanent permanent :permanents) {
                    sourceCreature.damage(permanent.getPower().getValue(), permanent.getId(), game, true, false);
                }
            }
            return true;
        }
        return false;
    }
}
