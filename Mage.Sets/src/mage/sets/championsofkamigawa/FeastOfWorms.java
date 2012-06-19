/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author LevelX
 */
public class FeastOfWorms extends CardImpl<FeastOfWorms> {

    private final static FilterControlledPermanent filter = new FilterControlledPermanent();
    static {
            filter.getCardType().add(CardType.LAND);
        }


    public FeastOfWorms (UUID ownerId) {
        super(ownerId, 216, "Feast of Worms", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{3}{G}{G}");
        this.expansionSetCode = "CHK";
        this.subtype.add("Arcane");
    this.color.setGreen(true);

        // Destroy target land. If that land was legendary, its controller sacrifices another land.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetLandPermanent());
        this.getSpellAbility().addEffect(new FeastOfWormsEffect());
    }

    public FeastOfWorms (final FeastOfWorms card) {
        super(card);
    }

    @Override
    public FeastOfWorms copy() {
        return new FeastOfWorms(this);
    }

}

class FeastOfWormsEffect extends OneShotEffect<FeastOfWormsEffect> {

    FeastOfWormsEffect() {
        super(Outcome.Sacrifice);
        staticText = "If that land was legendary, its controller sacrifices another land";
    }

    FeastOfWormsEffect(FeastOfWormsEffect effect) {
        super(effect);
    }

    @Override
    public FeastOfWormsEffect copy() {
        return new FeastOfWormsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        //Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = (Permanent) game.getLastKnownInformation(source.getFirstTarget(), Zone.BATTLEFIELD);
        Player targetPlayer = game.getPlayer(permanent.getControllerId());
        if (targetPlayer != null && permanent != null
                && (permanent.getSupertype().get(0).toString().equals("Legendary"))) {
                FilterControlledPermanent filter = new FilterControlledPermanent("land to sacrifice");
                filter.getCardType().add(CardType.LAND);
                filter.getControllerId().add(targetPlayer.getId());
                filter.setNotController(false);
                TargetControlledPermanent target = new TargetControlledPermanent(1, 1, filter, false);

                if (target.canChoose(targetPlayer.getId(), game)) {
                    targetPlayer.choose(Outcome.Sacrifice, target, source.getSourceId(), game);

                    Permanent land = game.getPermanent(target.getFirstTarget());
                    if (land != null) {
                        return land.sacrifice(source.getId(), game);
                    }
                    return true;
                }
        }
        return false;
    }
}
