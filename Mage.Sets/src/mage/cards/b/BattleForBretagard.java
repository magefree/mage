package mage.cards.b;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ElfWarriorToken;
import mage.game.permanent.token.HumanWarriorToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class BattleForBretagard extends CardImpl {

    public BattleForBretagard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}{W}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I — Create a 1/1 white Human Warrior creature token.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new CreateTokenEffect(new HumanWarriorToken()));

        // II — Create a 1/1 green Elf Warrior creature token.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new CreateTokenEffect(new ElfWarriorToken()));

        // III — Choose any number of artifact tokens and/or creature tokens you control with different names. For each of them, create a token thats a copy of it.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new BattleForBretagardEffect());

        this.addAbility(sagaAbility);
    }

    private BattleForBretagard(final BattleForBretagard card) {
        super(card);
    }

    @Override
    public BattleForBretagard copy() {
        return new BattleForBretagard(this);
    }
}

class BattleForBretagardEffect extends OneShotEffect {

    BattleForBretagardEffect() {
        super(Outcome.Benefit);
        staticText = "Choose any number of artifact tokens and/or " +
                "creature tokens you control with different names. " +
                "For each of them, create a token that's a copy of it.";
    }

    private BattleForBretagardEffect(final BattleForBretagardEffect effect) {
        super(effect);
    }

    @Override
    public BattleForBretagardEffect copy() {
        return new BattleForBretagardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new BattleForBretagardTarget();
        player.choose(outcome, target, source, game);
        for (UUID targetId : target.getTargets()) {
            new CreateTokenCopyTargetEffect()
                    .setTargetPointer(new FixedTarget(targetId, game))
                    .apply(game, source);
        }
        return true;
    }
}

class BattleForBretagardTarget extends TargetPermanent {

    private static final FilterPermanent filter = new FilterControlledPermanent(
            "artifact tokens and/or creature tokens you control with different names"
    );

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
        filter.add(TokenPredicate.TRUE);
    }

    BattleForBretagardTarget() {
        super(0, Integer.MAX_VALUE, filter, true);
    }

    private BattleForBretagardTarget(final BattleForBretagardTarget target) {
        super(target);
    }

    @Override
    public BattleForBretagardTarget copy() {
        return new BattleForBretagardTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability ability, Game game) {
        if (!super.canTarget(playerId, id, ability, game)) {
            return false;
        }
        Set<String> names = this.getTargets()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .map(MageObject::getName)
                .collect(Collectors.toSet());
        names.removeIf(Objects::isNull);
        names.removeIf(String::isEmpty);
        Permanent permanent = game.getPermanent(id);
        return permanent != null && !names.contains(permanent.getName());
    }


    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = super.possibleTargets(sourceControllerId, source, game);
        Set<String> names = this.getTargets()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .map(MageObject::getName)
                .collect(Collectors.toSet());
        names.removeIf(Objects::isNull);
        names.removeIf(String::isEmpty);
        possibleTargets.removeIf(uuid -> {
            Permanent permanent = game.getPermanent(uuid);
            return permanent == null || names.contains(permanent.getName());
        });
        return possibleTargets;
    }
}
