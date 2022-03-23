
package mage.cards.c;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.filter.FilterPermanent;
import mage.filter.common.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

public final class CatchRelease extends SplitCard {

    public CatchRelease(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}{R}", "{4}{R}{W}", SpellAbilityType.SPLIT_FUSED);

        // Catch
        // Gain control of target permanent until end of turn. Untap it. It gains haste until end of turn.
        getLeftHalfCard().getSpellAbility().addTarget(new TargetPermanent(new FilterPermanent()));
        getLeftHalfCard().getSpellAbility().addEffect(new GainControlTargetEffect(Duration.EndOfTurn));
        Effect effect = new UntapTargetEffect();
        effect.setText("Untap it");
        this.getSpellAbility().addEffect(effect);
        getLeftHalfCard().getSpellAbility().addEffect(effect);
        effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("It gains haste until end of turn");
        getLeftHalfCard().getSpellAbility().addEffect(effect);

        // Release
        // Each player sacrifices an artifact, a creature, an enchantment, a land, and a planeswalker.
        getRightHalfCard().getSpellAbility().addEffect(new ReleaseSacrificeEffect());

    }

    private CatchRelease(final CatchRelease card) {
        super(card);
    }

    @Override
    public CatchRelease copy() {
        return new CatchRelease(this);
    }
}

class ReleaseSacrificeEffect extends OneShotEffect {

    public ReleaseSacrificeEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Each player sacrifices an artifact, a creature, an enchantment, a land, and a planeswalker";
    }

    public ReleaseSacrificeEffect(ReleaseSacrificeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<UUID> chosen = new ArrayList<>();
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);

            Target target1 = new TargetControlledPermanent(1, 1, new FilterControlledArtifactPermanent(), true);
            Target target2 = new TargetControlledPermanent(1, 1, new FilterControlledCreaturePermanent(), true);
            Target target3 = new TargetControlledPermanent(1, 1, new FilterControlledEnchantmentPermanent(), true);
            Target target4 = new TargetControlledPermanent(1, 1, new FilterControlledLandPermanent(), true);
            Target target5 = new TargetControlledPermanent(1, 1, new FilterControlledPlaneswalkerPermanent(), true);

            if (target1.canChoose(player.getId(), source, game)) {
                while (player.canRespond() && !target1.isChosen() && target1.canChoose(player.getId(), source, game)) {
                    player.chooseTarget(Outcome.Benefit, target1, source, game);
                }
                Permanent artifact = game.getPermanent(target1.getFirstTarget());
                if (artifact != null) {
                    chosen.add(artifact.getId());
                }
                target1.clearChosen();
            }

            if (target2.canChoose(player.getId(), source, game)) {
                while (player.canRespond() && !target2.isChosen() && target2.canChoose(player.getId(), source, game)) {
                    player.chooseTarget(Outcome.Benefit, target2, source, game);
                }
                Permanent creature = game.getPermanent(target2.getFirstTarget());
                if (creature != null) {
                    chosen.add(creature.getId());
                }
                target2.clearChosen();
            }

            if (target3.canChoose(player.getId(), source, game)) {
                while (player.canRespond() && !target3.isChosen() && target3.canChoose(player.getId(), source, game)) {
                    player.chooseTarget(Outcome.Benefit, target3, source, game);
                }
                Permanent enchantment = game.getPermanent(target3.getFirstTarget());
                if (enchantment != null) {
                    chosen.add(enchantment.getId());
                }
                target3.clearChosen();
            }

            if (target4.canChoose(player.getId(), source, game)) {
                while (player.canRespond() && !target4.isChosen() && target4.canChoose(player.getId(), source, game)) {
                    player.chooseTarget(Outcome.Benefit, target4, source, game);
                }
                Permanent land = game.getPermanent(target4.getFirstTarget());
                if (land != null) {
                    chosen.add(land.getId());
                }
                target4.clearChosen();
            }

            if (target5.canChoose(player.getId(), source, game)) {
                while (player.canRespond() && !target5.isChosen() && target5.canChoose(player.getId(), source, game)) {
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
                permanent.sacrifice(source, game);
            }
        }
        return true;
    }

    @Override
    public ReleaseSacrificeEffect copy() {
        return new ReleaseSacrificeEffect(this);
    }
}
