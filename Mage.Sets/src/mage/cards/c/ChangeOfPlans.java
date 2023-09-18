package mage.cards.c;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ConniveSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class ChangeOfPlans extends CardImpl {

    public ChangeOfPlans(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{1}{U}");

        // Each of X target creatures you control connive. You may have any number of them phase out.
        this.getSpellAbility().addEffect(new ChangeOfPlansEffect());
        this.getSpellAbility().setTargetAdjuster(ChangeOfPlansAdjuster.instance);
    }

    private ChangeOfPlans(final ChangeOfPlans card) {
        super(card);
    }

    @Override
    public ChangeOfPlans copy() {
        return new ChangeOfPlans(this);
    }
}

enum ChangeOfPlansAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        ability.addTarget(new TargetControlledCreaturePermanent(ability.getManaCostsToPay().getX()));
    }
}

class ChangeOfPlansEffect extends OneShotEffect {

    ChangeOfPlansEffect() {
        super(Outcome.Benefit);
        staticText = "each of X target creatures you control connive. You may have any number of them phase out";
    }

    private ChangeOfPlansEffect(final ChangeOfPlansEffect effect) {
        super(effect);
    }

    @Override
    public ChangeOfPlansEffect copy() {
        return new ChangeOfPlansEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<Permanent> permanents = this
                .getTargetPointer()
                .getTargets(game, source)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        if (permanents.isEmpty()) {
            return false;
        }
        for (Permanent permanent : permanents) {
            ConniveSourceEffect.connive(permanent, 1, source, game);
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return true;
        }
        FilterPermanent filter = new FilterPermanent("creatures");
        filter.add(Predicates.or(
                permanents
                        .stream()
                        .map(MageItem::getId)
                        .map(PermanentIdPredicate::new)
                        .collect(Collectors.toSet())
        ));
        TargetPermanent target = new TargetPermanent(0, Integer.MAX_VALUE, filter, true);
        player.choose(outcome, target.withChooseHint("to phase out"), source, game);
        for (UUID targetId : target.getTargets()) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null) {
                permanent.phaseOut(game);
            }
        }
        return true;
    }
}
