package mage.cards.r;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromHandSourcePermanentCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CopySpellForEachItCouldTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterStackObject;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetStackObject;
import mage.watchers.common.CastFromHandWatcher;

import java.util.*;

/**
 * @author TheElk801
 */
public final class RadiantPerformer extends CardImpl {

    private static final FilterStackObject filter
            = new FilterStackObject("spell or ability that targets only a single permanent or player");

    static {
        filter.add(RadiantPerformerPredicate.instance);
    }

    public RadiantPerformer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Radiant Performer enters the battlefield, if you cast it from your hand, choose target spell or ability that targets only a single permanent or player. Copy that spell or ability for each other permanent or player the spell or ability could target. Each copy targets a different one of those permanents and players.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new RadiantPerformerEffect()),
                CastFromHandSourcePermanentCondition.instance, "When {this} enters the battlefield, " +
                "if you cast it from your hand, choose target spell or ability that targets only " +
                "a single permanent or player. Copy that spell or ability for each other permanent or player " +
                "the spell or ability could target. Each copy targets a different one of those permanents and players."
        );
        ability.addTarget(new TargetStackObject(filter));
        this.addAbility(ability, new CastFromHandWatcher());
    }

    private RadiantPerformer(final RadiantPerformer card) {
        super(card);
    }

    @Override
    public RadiantPerformer copy() {
        return new RadiantPerformer(this);
    }
}

enum RadiantPerformerPredicate implements Predicate<StackObject> {
    instance;

    @Override
    public boolean apply(StackObject input, Game game) {
        Ability ability = input instanceof Spell ? ((Spell) input).getSpellAbility() : (StackAbility) input;
        return ability != null
                && ability
                .getModes()
                .values()
                .stream()
                .map(Mode::getTargets)
                .flatMap(Collection::stream)
                .map(Target::getTargets)
                .flatMap(Collection::stream)
                .distinct()
                .mapToInt(uuid -> game.getPermanent(uuid) == null && game.getPlayer(uuid) == null ? 2 : 1)
                .sum() == 1;
    }
}

class RadiantPerformerEffect extends CopySpellForEachItCouldTargetEffect {

    RadiantPerformerEffect() {
        super();
    }

    private RadiantPerformerEffect(final RadiantPerformerEffect effect) {
        super(effect);
    }

    @Override
    protected StackObject getStackObject(Game game, Ability source) {
        return game.getStack().getStackObject(source.getFirstTarget());
    }

    @Override
    protected Player getPlayer(Game game, Ability source) {
        return game.getPlayer(source.getControllerId());
    }

    @Override
    protected List<MageObjectReferencePredicate> prepareCopiesWithTargets(StackObject stackObject, Player player, Ability source, Game game) {
        List<MageObjectReferencePredicate> predicates = new ArrayList<>();
        Ability ability = stackObject instanceof Spell ? ((Spell) stackObject).getSpellAbility() : (StackAbility) stackObject;
        UUID targeted = ability == null ? null : ability
                .getModes()
                .values()
                .stream()
                .map(Mode::getTargets)
                .flatMap(Collection::stream)
                .map(Target::getTargets)
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .findAny()
                .orElse(null);
        game.getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_PERMANENT, player.getId(), source, game
                ).stream()
                .filter(Objects::nonNull)
                .filter(p -> !p.equals(game.getPermanent(targeted)))
                .filter(p -> stackObject.canTarget(game, p.getId()))
                .map(p -> new MageObjectReference(p, game))
                .map(MageObjectReferencePredicate::new)
                .forEach(predicates::add);
        game.getState()
                .getPlayersInRange(source.getControllerId(), game)
                .stream()
                .filter(uuid -> !uuid.equals(targeted))
                .filter(uuid -> stackObject.canTarget(game, uuid))
                .map(MageObjectReference::new)
                .map(MageObjectReferencePredicate::new)
                .forEach(predicates::add);
        return predicates;
    }

    @Override
    public RadiantPerformerEffect copy() {
        return new RadiantPerformerEffect(this);
    }
}
