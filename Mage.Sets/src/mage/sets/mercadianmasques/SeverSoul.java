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
package mage.sets.mercadianmasques;

import java.util.UUID;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author dustinconrad
 */
public class SeverSoul extends CardImpl {

    private static final FilterCreaturePermanent nonBlackCreature = new FilterCreaturePermanent("nonblack creature");

    static {
        nonBlackCreature.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public SeverSoul(UUID ownerId) {
        super(ownerId, 159, "Sever Soul", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");
        this.expansionSetCode = "MMQ";


        // Destroy target nonblack creature. It can't be regenerated.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(nonBlackCreature));
        this.getSpellAbility().addEffect(new DestroyTargetEffect(true));
        // You gain life equal to its toughness.
        this.getSpellAbility().addEffect(new GainLifeEqualToToughnessEffect());
    }

    public SeverSoul(final SeverSoul card) {
        super(card);
    }

    @Override
    public SeverSoul copy() {
        return new SeverSoul(this);
    }
}

class GainLifeEqualToToughnessEffect extends OneShotEffect {

    public GainLifeEqualToToughnessEffect() {
        super(Outcome.GainLife);
        staticText = "You gain life equal to its toughness.";
    }

    public GainLifeEqualToToughnessEffect(final GainLifeEqualToToughnessEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                player.gainLife(permanent.getToughness().getValue(), game);
            }
        }
        return false;
    }

    @Override
    public GainLifeEqualToToughnessEffect copy() {
        return new GainLifeEqualToToughnessEffect(this);
    }
}
