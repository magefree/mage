package mage.cards.o;

import mage.MageInt;
import mage.MageItem;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.DiscardedByOpponentTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class OrvarTheAllForm extends CardImpl {

    public OrvarTheAllForm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Changeling
        this.addAbility(new ChangelingAbility());

        // Whenever you cast an instant or sorcery spell, if it targets one or more other permanents you control, create a token that's a copy of one of those permanents.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new SpellCastControllerTriggeredAbility(
                        new OrvarTheAllFormEffect(),
                        StaticFilters.FILTER_SPELL_INSTANT_OR_SORCERY,
                        false, true
                ), OrvarTheAllFormCondition.instance, "Whenever you cast an instant or sorcery spell, " +
                "if it targets one or more other permanents you control, " +
                "create a token that's a copy of one of those permanents."
        ));

        // When a spell or ability an opponent controls causes you to discard this card, create a token that's a copy of target permanent.
        Ability ability = new DiscardedByOpponentTriggeredAbility(new CreateTokenCopyTargetEffect());
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    private OrvarTheAllForm(final OrvarTheAllForm card) {
        super(card);
    }

    @Override
    public OrvarTheAllForm copy() {
        return new OrvarTheAllForm(this);
    }
}

enum OrvarTheAllFormCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell) source.getEffects().get(0).getValue("spellCast");
        MageObjectReference mor;
        if (source.getSourceObjectZoneChangeCounter() == 0) {
            mor = new MageObjectReference(source.getSourceId(), game);
        } else {
            mor = new MageObjectReference(source);
        }
        return spell != null && spell
                .getSpellAbility()
                .getModes()
                .values()
                .stream()
                .map(Mode::getTargets)
                .flatMap(Collection::stream)
                .map(Target::getTargets)
                .flatMap(Collection::stream)
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .filter(p -> !mor.refersTo(p, game))
                .map(Controllable::getControllerId)
                .anyMatch(source::isControlledBy);
    }
}

class OrvarTheAllFormEffect extends OneShotEffect {

    OrvarTheAllFormEffect() {
        super(Outcome.Benefit);
    }

    private OrvarTheAllFormEffect(final OrvarTheAllFormEffect effect) {
        super(effect);
    }

    @Override
    public OrvarTheAllFormEffect copy() {
        return new OrvarTheAllFormEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Spell spell = (Spell) this.getValue("spellCast");
        if (player == null || spell == null) {
            return false;
        }
        List<Predicate<Permanent>> predicates = spell
                .getSpellAbility()
                .getModes()
                .values()
                .stream()
                .map(Mode::getTargets)
                .flatMap(Collection::stream)
                .map(Target::getTargets)
                .flatMap(Collection::stream)
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .map(MageItem::getId)
                .map(PermanentIdPredicate::new)
                .collect(Collectors.toList());
        if (predicates.isEmpty()) {
            return false;
        }
        FilterPermanent filter = new FilterControlledPermanent("a permanent you control targeted by that spell");
        filter.add(Predicates.or(predicates));
        filter.add(Predicates.not(new MageObjectReferencePredicate(new MageObjectReference(source))));
        TargetPermanent target = new TargetPermanent(filter);
        target.setNotTarget(true);
        player.choose(outcome, target, source, game);
        return new CreateTokenCopyTargetEffect()
                .setTargetPointer(new FixedTarget(target.getFirstTarget(), game))
                .apply(game, source);
    }
}
