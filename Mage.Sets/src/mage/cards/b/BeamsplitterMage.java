package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.AbilityImpl;
import mage.abilities.Mode;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.util.functions.StackObjectCopyApplier;

import java.util.*;

/**
 * @author TheElk801
 */
public final class BeamsplitterMage extends CardImpl {

    public BeamsplitterMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{R}");

        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast an instant or sorcery spell that targets only Beamsplitter Mage, if you control one or more creatures that spell could target, choose one of those creatures. Copy that spell. The copy targets the chosen creature.
        this.addAbility(new BeamsplitterMageTriggeredAbility());
    }

    private BeamsplitterMage(final BeamsplitterMage card) {
        super(card);
    }

    @Override
    public BeamsplitterMage copy() {
        return new BeamsplitterMage(this);
    }
}

class BeamsplitterMageTriggeredAbility extends TriggeredAbilityImpl {

    BeamsplitterMageTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BeamsplitterMageEffect(), false);
    }

    private BeamsplitterMageTriggeredAbility(final BeamsplitterMageTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BeamsplitterMageTriggeredAbility copy() {
        return new BeamsplitterMageTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId())) {
            return false;
        }
        Spell spell = game.getSpellOrLKIStack(event.getTargetId());
        if (spell == null || !spell.isInstantOrSorcery(game)) {
            return false;
        }
        if (spell.getSpellAbilities()
                .stream()
                .map(AbilityImpl::getModes)
                .flatMap(m -> m.getSelectedModes().stream().map(m::get))
                .filter(Objects::nonNull)
                .map(Mode::getTargets)
                .flatMap(Collection::stream)
                .filter(t -> !t.isNotTarget())
                .map(Target::getTargets)
                .flatMap(Collection::stream)
                .anyMatch(uuid -> !getSourceId().equals(uuid) && uuid != null)) {
            return false;
        }
        this.getEffects().setValue("spellCast", spell);
        return true;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        Spell spell = (Spell) getEffects().get(0).getValue("spellCast");
        if (spell == null) {
            return false;
        }
        return game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_CREATURE,
                getControllerId(), this, game
        ).stream()
                .filter(Objects::nonNull)
                .filter(permanent -> permanent.isCreature(game))
                .filter(p -> checkNotSource(p, game))
                .anyMatch(p -> spell.canTarget(game, p.getId()));
    }

    private boolean checkNotSource(Permanent permanent, Game game) {
        // workaround for zcc not being set before first intervening if check
        if (this.getSourceObjectZoneChangeCounter() == 0) {
            return !permanent.getId().equals(this.getSourceId());
        }
        return !permanent.getId().equals(this.getSourceId())
                || permanent.getZoneChangeCounter(game) != this.getSourceObjectZoneChangeCounter();
    }

    @Override
    public String getRule() {
        return "Whenever you cast an instant or sorcery spell that targets "
                + "only {this}, if you control one or more other creatures "
                + "that spell could target, choose one of those creatures. "
                + "Copy that spell. The copy targets the chosen creature.";
    }
}

class BeamsplitterMageEffect extends OneShotEffect {

    BeamsplitterMageEffect() {
        super(Outcome.Detriment);
    }

    private BeamsplitterMageEffect(final BeamsplitterMageEffect effect) {
        super(effect);
    }

    @Override
    public BeamsplitterMageEffect copy() {
        return new BeamsplitterMageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Spell spell = (Spell) getValue("spellCast");
        if (player == null || spell == null) {
            return false;
        }
        FilterPermanent filter = new FilterControlledCreaturePermanent("a creature you control that can be targeted by " + spell.getName());
        filter.add(AnotherPredicate.instance);
        filter.add(new BeamsplitterMagePredicate(spell));
        TargetPermanent target = new TargetPermanent(filter);
        target.setNotTarget(true);
        player.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        spell.createCopyOnStack(
                game, source, player.getId(), false,
                1, new BeamsplitterMageApplier(permanent, game)
        );
        return true;
    }
}

class BeamsplitterMagePredicate implements Predicate<Permanent> {

    private final Spell spell;

    BeamsplitterMagePredicate(Spell spell) {
        this.spell = spell;
    }

    @Override
    public boolean apply(Permanent input, Game game) {
        return spell != null && spell.canTarget(game, input.getId());
    }
}

class BeamsplitterMageApplier implements StackObjectCopyApplier {

    private final Iterator<MageObjectReferencePredicate> predicate;

    BeamsplitterMageApplier(Permanent permanent, Game game) {
        this.predicate = Arrays.asList(new MageObjectReferencePredicate(permanent, game)).iterator();
    }

    @Override
    public void modifySpell(StackObject stackObject, Game game) {
    }

    @Override
    public MageObjectReferencePredicate getNextNewTargetType() {
        if (predicate.hasNext()) {
            return predicate.next();
        }
        return null;
    }
}
