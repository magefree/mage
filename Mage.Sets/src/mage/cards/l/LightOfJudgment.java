package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetImpl;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class LightOfJudgment extends CardImpl {

    public LightOfJudgment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{R}");

        // Light of Judgment deals 6 damage to target creature. Destroy up to one Equipment attached to that creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(6));
        this.getSpellAbility().addEffect(new LightOfJudgmentEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private LightOfJudgment(final LightOfJudgment card) {
        super(card);
    }

    @Override
    public LightOfJudgment copy() {
        return new LightOfJudgment(this);
    }
}

class LightOfJudgmentEffect extends OneShotEffect {

    LightOfJudgmentEffect() {
        super(Outcome.Benefit);
        staticText = "Destroy up to one Equipment attached to that creature";
    }

    private LightOfJudgmentEffect(final LightOfJudgmentEffect effect) {
        super(effect);
    }

    @Override
    public LightOfJudgmentEffect copy() {
        return new LightOfJudgmentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player == null || permanent == null || permanent.getAttachments().isEmpty()) {
            return false;
        }
        FilterPermanent filter = new FilterPermanent(SubType.EQUIPMENT, "Equipment attached to " + permanent.getIdName());
        filter.add(Predicates.or(
                permanent
                        .getAttachments()
                        .stream()
                        .map(PermanentIdPredicate::new)
                        .collect(Collectors.toSet())
        ));
        TargetPermanent target = new TargetPermanent(0, 1, filter, true);
        player.choose(outcome, target, source, game);
        return Optional
                .ofNullable(target)
                .map(TargetImpl::getFirstTarget)
                .map(game::getPermanent)
                .map(p -> p.destroy(source, game))
                .orElse(false);
    }
}
