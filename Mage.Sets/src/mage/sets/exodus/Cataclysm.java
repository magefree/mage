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
package mage.sets.exodus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author jeffwadsworth
 */
public class Cataclysm extends CardImpl<Cataclysm> {

    public Cataclysm(UUID ownerId) {
        super(ownerId, 3, "Cataclysm", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{2}{W}{W}");
        this.expansionSetCode = "EXO";

        this.color.setWhite(true);

        // Each player chooses from the permanents he or she controls an artifact, a creature, an enchantment, and a land, then sacrifices the rest.
        this.getSpellAbility().addEffect(new CataclysmEffect());
    }

    public Cataclysm(final Cataclysm card) {
        super(card);
    }

    @Override
    public Cataclysm copy() {
        return new Cataclysm(this);
    }
}

class CataclysmEffect extends OneShotEffect<CataclysmEffect> {

    private static final FilterArtifactPermanent filter1 = new FilterArtifactPermanent("artifact you control");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creature you control");
    private static final FilterEnchantmentPermanent filter3 = new FilterEnchantmentPermanent("enchantment you control");
    private static final FilterLandPermanent filter4 = new FilterLandPermanent("land you control");

    static {
        filter1.add(new ControllerPredicate(Constants.TargetController.YOU));
        filter2.add(new ControllerPredicate(Constants.TargetController.YOU));
        filter3.add(new ControllerPredicate(Constants.TargetController.YOU));
        filter4.add(new ControllerPredicate(Constants.TargetController.YOU));
    }

    public CataclysmEffect() {
        super(Constants.Outcome.DestroyPermanent);
        staticText = "Each player chooses from the permanents he or she controls an artifact, a creature, an enchantment, and a land, then sacrifices the rest";
    }

    public CataclysmEffect(CataclysmEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Card> chosen = new ArrayList<Card>();

        for (UUID playerId : game.getPlayerList()) {
            Player player = game.getPlayer(playerId);

            Target target1 = new TargetControlledPermanent(1, 1, filter1, false);
            Target target2 = new TargetControlledPermanent(1, 1, filter2, false);
            Target target3 = new TargetControlledPermanent(1, 1, filter3, false);
            Target target4 = new TargetControlledPermanent(1, 1, filter4, false);

            target1.setRequired(true);
            target2.setRequired(true);
            target3.setRequired(true);
            target4.setRequired(true);

            target1.setNotTarget(true);
            target2.setNotTarget(true);
            target3.setNotTarget(true);
            target4.setNotTarget(true);

            if (target1.canChoose(player.getId(), game)) {
                while (!target1.isChosen() && target1.canChoose(player.getId(), game)) {
                    player.choose(Constants.Outcome.Benefit, target1, source.getSourceId(), game);
                }
                Permanent artifact = game.getPermanent(target1.getFirstTarget());
                if (artifact != null) {
                    chosen.add(artifact);
                }
                target1.clearChosen();
            }

            if (target2.canChoose(player.getId(), game)) {
                while (!target2.isChosen() && target2.canChoose(player.getId(), game)) {
                    player.choose(Constants.Outcome.Benefit, target2, source.getSourceId(), game);
                }
                Permanent creature = game.getPermanent(target2.getFirstTarget());
                if (creature != null) {
                    chosen.add(creature);
                }
                target2.clearChosen();
            }

            if (target3.canChoose(player.getId(), game)) {
                while (!target3.isChosen() && target3.canChoose(player.getId(), game)) {
                    player.choose(Constants.Outcome.Benefit, target3, source.getSourceId(), game);
                }
                Permanent enchantment = game.getPermanent(target3.getFirstTarget());
                if (enchantment != null) {
                    chosen.add(enchantment);
                }
                target3.clearChosen();
            }
            
            if (target4.canChoose(player.getId(), game)) {
                while (!target4.isChosen() && target4.canChoose(player.getId(), game)) {
                    player.choose(Constants.Outcome.Benefit, target4, source.getSourceId(), game);
                }
                Permanent land = game.getPermanent(target4.getFirstTarget());
                if (land != null) {
                    chosen.add(land);
                }
                target4.clearChosen();
            }

        }

        for (Permanent permanent : game.getBattlefield().getAllActivePermanents()) {
            if (!chosen.contains(permanent)) {
                permanent.sacrifice(source.getId(), game);
            }
        }
        return true;
    }

    @Override
    public CataclysmEffect copy() {
        return new CataclysmEffect(this);
    }
}
