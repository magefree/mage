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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.effects.common.continious.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.SplitCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.common.FilterPlaneswalkerPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */


public class CatchRelease extends SplitCard<CatchRelease> {

    public CatchRelease(UUID ownerId) {
        super(ownerId, 125, "Catch", "Release", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{1}{U}{R}", "{4}{R}{W}",true);
        this.expansionSetCode = "DGM";

        this.color.setBlue(true);
        this.color.setRed(true);
        this.color.setWhite(true);

        // Catch
        // Gain control of target permanent until end of turn. Untap it. It gains haste until end of turn.
        getLeftHalfCard().getColor().setRed(true);
        getLeftHalfCard().getSpellAbility().addTarget(new TargetPermanent(new FilterPermanent()));
        getLeftHalfCard().getSpellAbility().addEffect(new GainControlTargetEffect(Duration.EndOfTurn));
        getLeftHalfCard().getSpellAbility().addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn));
        getLeftHalfCard().getSpellAbility().addEffect(new UntapTargetEffect());

        // Release
        // Each player sacrifices an artifact, a creature, an enchantment, a land, and a planeswalker.
        getRightHalfCard().getColor().setGreen(true);
        getRightHalfCard().getSpellAbility().addEffect(new ReleaseSacrificeEffect());   

    }

    public CatchRelease(final CatchRelease card) {
        super(card);
    }

    @Override
    public CatchRelease copy() {
        return new CatchRelease(this);
    }
}

class ReleaseSacrificeEffect extends OneShotEffect<ReleaseSacrificeEffect> {

    private static final FilterArtifactPermanent filter1 = new FilterArtifactPermanent("artifact you control");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creature you control");
    private static final FilterEnchantmentPermanent filter3 = new FilterEnchantmentPermanent("enchantment you control");
    private static final FilterLandPermanent filter4 = new FilterLandPermanent("land you control");
    private static final FilterPlaneswalkerPermanent filter5 = new FilterPlaneswalkerPermanent("planeswalker you control");

    static {
        filter1.add(new ControllerPredicate(Constants.TargetController.YOU));
        filter2.add(new ControllerPredicate(Constants.TargetController.YOU));
        filter3.add(new ControllerPredicate(Constants.TargetController.YOU));
        filter4.add(new ControllerPredicate(Constants.TargetController.YOU));
        filter5.add(new ControllerPredicate(Constants.TargetController.YOU));
    }

    public ReleaseSacrificeEffect() {
        super(Constants.Outcome.DestroyPermanent);
        staticText = "Each player sacrifices an artifact, a creature, an enchantment, a land, and a planeswalker";
    }

    public ReleaseSacrificeEffect(ReleaseSacrificeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<UUID> chosen = new ArrayList<UUID>();
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        for (UUID playerId : controller.getInRange()) {
            Player player = game.getPlayer(playerId);

            Target target1 = new TargetControlledPermanent(1, 1, filter1, false);
            Target target2 = new TargetControlledPermanent(1, 1, filter2, false);
            Target target3 = new TargetControlledPermanent(1, 1, filter3, false);
            Target target4 = new TargetControlledPermanent(1, 1, filter4, false);
            Target target5 = new TargetControlledPermanent(1, 1, filter5, false);

            target1.setRequired(true);
            target2.setRequired(true);
            target3.setRequired(true);
            target4.setRequired(true);
            target5.setRequired(true);

            target1.setNotTarget(false);
            target2.setNotTarget(false);
            target3.setNotTarget(false);
            target4.setNotTarget(false);
            target5.setNotTarget(false);

            if (target1.canChoose(player.getId(), game)) {
                while (!target1.isChosen() && target1.canChoose(player.getId(), game)) {
                    player.chooseTarget(Outcome.Benefit, target1, source, game);
                }
                Permanent artifact = game.getPermanent(target1.getFirstTarget());
                if (artifact != null) {
                    chosen.add(artifact.getId());
                }
                target1.clearChosen();
            }

            if (target2.canChoose(player.getId(), game)) {
                while (!target2.isChosen() && target2.canChoose(player.getId(), game)) {
                    player.chooseTarget(Outcome.Benefit, target2, source, game);
                }
                Permanent creature = game.getPermanent(target2.getFirstTarget());
                if (creature != null) {
                    chosen.add(creature.getId());
                }
                target2.clearChosen();
            }

            if (target3.canChoose(player.getId(), game)) {
                while (!target3.isChosen() && target3.canChoose(player.getId(), game)) {
                    player.chooseTarget(Outcome.Benefit, target3, source, game);
                }
                Permanent enchantment = game.getPermanent(target3.getFirstTarget());
                if (enchantment != null) {
                    chosen.add(enchantment.getId());
                }
                target3.clearChosen();
            }

            if (target4.canChoose(player.getId(), game)) {
                while (!target4.isChosen() && target4.canChoose(player.getId(), game)) {
                    player.chooseTarget(Outcome.Benefit, target4, source, game);
                }
                Permanent land = game.getPermanent(target4.getFirstTarget());
                if (land != null) {
                    chosen.add(land.getId());
                }
                target4.clearChosen();
            }

            if (target5.canChoose(player.getId(), game)) {
                while (!target5.isChosen() && target5.canChoose(player.getId(), game)) {
                    player.chooseTarget(Outcome.Benefit, target5, source, game);
                }
                Permanent planeswalker = game.getPermanent(target5.getFirstTarget());
                if (planeswalker != null) {
                    chosen.add(planeswalker.getId());
                }
                target5.clearChosen();
            }

        }

        for (UUID uuid : chosen) {
            Permanent permanent = game.getPermanent(uuid);
            if (permanent != null) {
                permanent.sacrifice(source.getId(), game);
            }
        }
        return true;
    }

    @Override
    public ReleaseSacrificeEffect copy() {
        return new ReleaseSacrificeEffect(this);
    }
}
