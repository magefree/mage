package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.predicate.permanent.EquippedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AwakenTheSleeper extends CardImpl {

    public AwakenTheSleeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Gain control of target creature until end of turn. Untap that creature. It gains haste until end of turn. If it's equipped, you may destroy all Equipment attached to that creature.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new UntapTargetEffect().setText("Untap that creature"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("It gains haste until end of turn."));
        this.getSpellAbility().addEffect(new AwakenTheSleeperEffect());
    }

    private AwakenTheSleeper(final AwakenTheSleeper card) {
        super(card);
    }

    @Override
    public AwakenTheSleeper copy() {
        return new AwakenTheSleeper(this);
    }
}

class AwakenTheSleeperEffect extends OneShotEffect {

    AwakenTheSleeperEffect() {
        super(Outcome.Benefit);
        staticText = "If it's equipped, you may destroy all Equipment attached to that creature";
    }

    private AwakenTheSleeperEffect(final AwakenTheSleeperEffect effect) {
        super(effect);
    }

    @Override
    public AwakenTheSleeperEffect copy() {
        return new AwakenTheSleeperEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player == null || permanent == null
                || !EquippedPredicate.instance.apply(permanent, game)
                || !player.chooseUse(outcome, "Destroy all equipment attached to " + permanent.getName() + '?', source, game)) {
            return false;
        }
        List<Permanent> toDestroy = new LinkedList<>();
        for (UUID attachmentId : permanent.getAttachments()) {
            Permanent attachment = game.getPermanent(attachmentId);
            if (attachment != null && attachment.hasSubtype(SubType.EQUIPMENT, game)) {
                toDestroy.add(attachment);
            }
        }
        for (Permanent equipment : toDestroy) {
            equipment.destroy(source, game);
        }
        return true;
    }
}
