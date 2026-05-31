package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.ManaPoolItem;
import mage.target.TargetPermanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author xenohedron
 */
public final class GrellPhilosopher extends CardImpl {

    public GrellPhilosopher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        
        this.subtype.add(SubType.HORROR);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Aberrant Tinkering — When Grell Philosopher enters and at the beginning of your upkeep, each Horror you control gains all activated abilities of target artifact an opponent controls until end of turn. You may spend blue mana as thought it were mana of any color to activate those abilities.
        Ability ability = new GrellPhilosopherTriggeredAbility();
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_ARTIFACT));
        this.addAbility(ability);
    }

    private GrellPhilosopher(final GrellPhilosopher card) {
        super(card);
    }

    @Override
    public GrellPhilosopher copy() {
        return new GrellPhilosopher(this);
    }
}

class GrellPhilosopherTriggeredAbility extends TriggeredAbilityImpl {

    GrellPhilosopherTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GrellPhilosopherEffect());
        this.withFlavorWord("Aberrant Tinkering");
        setTriggerPhrase("When {this} enters and at the beginning of your upkeep, ");
    }

    private GrellPhilosopherTriggeredAbility(final GrellPhilosopherTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GrellPhilosopherTriggeredAbility copy() {
        return new GrellPhilosopherTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD
                || event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            return event.getTargetId().equals(getSourceId());
        }
        return game.isActivePlayer(getControllerId());
    }
}

class GrellPhilosopherEffect extends ContinuousEffectImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.HORROR);

    GrellPhilosopherEffect() {
        super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "each Horror you control gains all activated abilities of target artifact " +
                "an opponent controls until end of turn. You may spend blue mana as though " +
                "it were mana of any color to activate those abilities";
    }

    private GrellPhilosopherEffect(final GrellPhilosopherEffect effect) {
        super(effect);
    }

    @Override
    public GrellPhilosopherEffect copy() {
        return new GrellPhilosopherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent artifact = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (artifact == null) {
            return false;
        }
        List<UUID> abilityIds = new ArrayList<>();
        game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)
                .forEach(p -> {
                    for (ActivatedAbility ability : artifact.getAbilities(game).getActivatedAbilities(Zone.BATTLEFIELD)) {
                        Ability a = p.addAbility(ability, source.getSourceId(), game, true);
                        abilityIds.add(a.getId());
                    }
                });
        game.addEffect(new GrellPhilosopherBlueManaEffect(abilityIds), source);
        return true;
    }
}

class GrellPhilosopherBlueManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    private final List<UUID> abilityIds = new ArrayList<>();

    GrellPhilosopherBlueManaEffect(List<UUID> abilityIds) {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.EndOfTurn, Outcome.Benefit);
        this.abilityIds.addAll(abilityIds);
    }

    private GrellPhilosopherBlueManaEffect(final GrellPhilosopherBlueManaEffect effect) {
        super(effect);
        this.abilityIds.addAll(effect.abilityIds);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public GrellPhilosopherBlueManaEffect copy() {
        return new GrellPhilosopherBlueManaEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return source.isControlledBy(affectedControllerId)
                && abilityIds.contains(source.getId());
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        if (mana.getBlue() > 0) {
            return ManaType.BLUE;
        }
        return null;
    }
}
