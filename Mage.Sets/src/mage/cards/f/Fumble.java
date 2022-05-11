package mage.cards.f;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class Fumble extends CardImpl {

    public Fumble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Return target creature to its owner's hand. Gain control of all Auras and Equipment that were attached to it, then attach them to another creature.
        this.getSpellAbility().addEffect(new FumbleEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Fumble(final Fumble card) {
        super(card);
    }

    @Override
    public Fumble copy() {
        return new Fumble(this);
    }
}

class FumbleEffect extends OneShotEffect {

    FumbleEffect() {
        super(Outcome.Benefit);
        this.staticText = "return target creature to its owner's hand. "
                + "Gain control of all Auras and Equipment that were attached to it, "
                + "then attach them to another creature";
    }

    FumbleEffect(final FumbleEffect effect) {
        super(effect);
    }

    @Override
    public FumbleEffect copy() {
        return new FumbleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getTargets().getFirstTarget());
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || permanent == null) {
            return false;
        }
        List<Permanent> attachments = new ArrayList<>();
        for (UUID permId : permanent.getAttachments()) {
            Permanent attachment = game.getPermanent(permId);
            if (attachment != null) {
                if (attachment.hasSubtype(SubType.AURA, game) || attachment.hasSubtype(SubType.EQUIPMENT, game)) {
                    attachments.add(attachment);
                }
            }
        }
        
        new ReturnToHandTargetEffect().apply(game, source);

        if (!attachments.isEmpty()) {
            Target target = new TargetCreaturePermanent(1, 1, StaticFilters.FILTER_PERMANENT_CREATURE, true);
            Permanent newCreature = null;
            if (player.choose(Outcome.BoostCreature, target, source, game)) {
                newCreature = game.getPermanent(target.getFirstTarget());
            }
            for (Permanent attachment : attachments) {
                if (!attachment.hasSubtype(SubType.AURA, game) && !attachment.hasSubtype(SubType.EQUIPMENT, game)) {
                    continue;
                }
                ContinuousEffect effect = new GainControlTargetEffect(Duration.Custom, true, player.getId());
                effect.setTargetPointer(new FixedTarget(attachment, game));
                game.addEffect(effect, source);
                if (newCreature != null) {
                    attachment.attachTo(newCreature.getId(), source, game);
                }
            }
        }
        return true;
    }
}
