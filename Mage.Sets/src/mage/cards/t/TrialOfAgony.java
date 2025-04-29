package mage.cards.t;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetPermanentSameController;
import mage.target.targetpointer.FixedTarget;
import mage.util.RandomUtil;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class TrialOfAgony extends CardImpl {

    public TrialOfAgony(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // Choose two target creatures controlled by the same opponent. That player chooses one of those creatures. Trial of Agony deals 5 damage to that creature, and the other can't block this turn.
        this.getSpellAbility().addEffect(new TrialOfAgonyEffect());
        this.getSpellAbility().addTarget(new TargetPermanentSameController(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES));
    }

    private TrialOfAgony(final TrialOfAgony card) {
        super(card);
    }

    @Override
    public TrialOfAgony copy() {
        return new TrialOfAgony(this);
    }
}

class TrialOfAgonyEffect extends OneShotEffect {

    TrialOfAgonyEffect() {
        super(Outcome.Benefit);
        staticText = "choose two target creatures controlled by the same opponent. " +
                "That player chooses one of those creatures. " +
                "{this} deals 5 damage to that creature, and the other can't block this turn";
    }

    private TrialOfAgonyEffect(final TrialOfAgonyEffect effect) {
        super(effect);
    }

    @Override
    public TrialOfAgonyEffect copy() {
        return new TrialOfAgonyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = this
                .getTargetPointer()
                .getTargets(game, source)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        Permanent toDamage;
        Permanent cantBlock;
        switch (permanents.size()) {
            case 0:
                return false;
            case 1:
                toDamage = permanents.get(0);
                cantBlock = null;
                break;
            default:
                Player player = game.getPlayer(permanents.get(0).getControllerId());
                if (player == null) {
                    toDamage = permanents.get(0);
                    cantBlock = permanents.get(1);
                    break;
                }
                FilterPermanent filter = new FilterPermanent();
                filter.add(Predicates.or(
                        permanents
                                .stream()
                                .map(MageItem::getId)
                                .map(ControllerIdPredicate::new)
                                .collect(Collectors.toList())
                ));
                TargetPermanent target = new TargetPermanent(filter);
                target.withChooseHint("to deal damage to");
                target.withNotTarget(true);
                player.choose(outcome, target, source, game);
                toDamage = game.getPermanent(target.getFirstTarget());
                permanents.remove(toDamage);
                cantBlock = RandomUtil.randomFromCollection(permanents);
        }
        if (toDamage != null) {
            toDamage.damage(5, source, game);
        }
        if (cantBlock != null) {
            game.addEffect(new CantBlockTargetEffect(Duration.EndOfTurn)
                    .setTargetPointer(new FixedTarget(cantBlock, game)), source);
        }
        return true;
    }
}
