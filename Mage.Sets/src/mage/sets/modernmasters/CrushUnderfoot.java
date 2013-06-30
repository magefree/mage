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
package mage.sets.modernmasters;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class CrushUnderfoot extends CardImpl<CrushUnderfoot> {

    public CrushUnderfoot(UUID ownerId) {
        super(ownerId, 109, "Crush Underfoot", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{R}");
        this.expansionSetCode = "MMA";
        this.supertype.add("Tribal");
        this.subtype.add("Giant");

        this.color.setRed(true);

        // Choose a Giant creature you control. It deals damage equal to its power to target creature.
        this.getSpellAbility().addEffect(new CrushUnderfootEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(new FilterCreaturePermanent("creature damage is dealt to")));

    }

    public CrushUnderfoot(final CrushUnderfoot card) {
        super(card);
    }

    @Override
    public CrushUnderfoot copy() {
        return new CrushUnderfoot(this);
    }
}

class CrushUnderfootEffect extends OneShotEffect<CrushUnderfootEffect> {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Giant you control");
    static {
        filter.add(new SubtypePredicate("Giant"));
    }

    public CrushUnderfootEffect() {
        super(Outcome.Damage);
        this.staticText = "Choose a Giant creature you control. It deals damage equal to its power to target creature";
    }

    public CrushUnderfootEffect(final CrushUnderfootEffect effect) {
        super(effect);
    }

    @Override
    public CrushUnderfootEffect copy() {
        return new CrushUnderfootEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            // Choose a Giant creature you control (not targeted, happens during effect resolving )
            Target target = new TargetControlledCreaturePermanent(1,1, filter,false);
            if (target.canChoose(source.getSourceId(), controller.getId(), game)
                    && controller.chooseTarget(outcome, target, source, game)) {
                Permanent giant = game.getPermanent(target.getFirstTarget());                
                if (giant != null) {
                    game.informPlayers(new StringBuilder("Crush Underfoot: Choosen Giant is").append(giant.getName()).toString());
                    Permanent targetCreature = game.getPermanent(this.getTargetPointer().getFirst(game, source));
                    if (targetCreature != null) {
                        targetCreature.damage(giant.getPower().getValue(), source.getSourceId(), game, true, false);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
