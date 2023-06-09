package mage.game.command.planes;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MainPhaseStackEmptyCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.RollPlanarDieEffect;
import mage.abilities.effects.common.cost.PlanarDieRollCostIncreasingEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.Card;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.command.Plane;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInLibrary;
import mage.watchers.common.PlanarRollWatcher;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * @author spjspj
 */
public class TrailOfTheMageRingsPlane extends Plane {

    private static final FilterCard filter = new FilterCard("creature spells");

    static {
        filter.add(CardType.CREATURE.getPredicate());
    }

    public TrailOfTheMageRingsPlane() {
        this.setPlaneType(Planes.PLANE_TRAIL_OF_THE_MAGE_RINGS);

        // Instant and sorcery spells have rebound
        Ability ability = new SimpleStaticAbility(Zone.COMMAND, new TrailOfTheMageRingsReboundEffect());
        this.getAbilities().add(ability);

        // Active player can roll the planar die: Whenever you roll {CHAOS}, you may search your library for an instant or sorcery card, reveal it, put it into your hand, then shuffle your library
        Effect chaosEffect = new SearchLibraryPutInHandEffect(new TargetCardInLibrary(0, 1, new FilterInstantOrSorceryCard()), true);
        Target chaosTarget = null;

        List<Effect> chaosEffects = new ArrayList<Effect>();
        chaosEffects.add(chaosEffect);
        List<Target> chaosTargets = new ArrayList<Target>();
        chaosTargets.add(chaosTarget);

        ActivateIfConditionActivatedAbility chaosAbility = new ActivateIfConditionActivatedAbility(Zone.COMMAND, new RollPlanarDieEffect(chaosEffects, chaosTargets), new GenericManaCost(0), MainPhaseStackEmptyCondition.instance);
        chaosAbility.addWatcher(new PlanarRollWatcher());
        this.getAbilities().add(chaosAbility);
        chaosAbility.setMayActivate(TargetController.ANY);
        this.getAbilities().add(new SimpleStaticAbility(Zone.ALL, new PlanarDieRollCostIncreasingEffect(chaosAbility.getOriginalId())));
    }

    private TrailOfTheMageRingsPlane(final TrailOfTheMageRingsPlane plane) {
        super(plane);
    }

    @Override
    public TrailOfTheMageRingsPlane copy() {
        return new TrailOfTheMageRingsPlane(this);
    }
}

class TrailOfTheMageRingsReboundEffect extends ContinuousEffectImpl {

    protected static final FilterCard filter = new FilterCard("Instant and sorcery spells");

    static {
        filter.add(Predicates.or(CardType.INSTANT.getPredicate(), CardType.SORCERY.getPredicate()));
    }

    public TrailOfTheMageRingsReboundEffect() {
        super(Duration.Custom, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "Instant and sorcery spells have rebound";
    }

    public TrailOfTheMageRingsReboundEffect(final TrailOfTheMageRingsReboundEffect effect) {
        super(effect);
    }

    @Override
    public TrailOfTheMageRingsReboundEffect copy() {
        return new TrailOfTheMageRingsReboundEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Plane cPlane = game.getState().getCurrentPlane();
        if (cPlane == null) {
            return false;
        }
        if (!cPlane.getPlaneType().equals(Planes.PLANE_TRAIL_OF_THE_MAGE_RINGS)) {
            return false;
        }

        for (UUID playerId : game.getPlayers().keySet()) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                for (Card card : player.getHand().getCards(filter, game)) {
                    addReboundAbility(card, source, game);
                }
                for (Iterator<StackObject> iterator = game.getStack().iterator(); iterator.hasNext(); ) {
                    StackObject stackObject = iterator.next();
                    if (stackObject instanceof Spell && stackObject.isControlledBy(source.getControllerId())) {
                        Spell spell = (Spell) stackObject;
                        Card card = spell.getCard();
                        if (card != null) {
                            addReboundAbility(card, source, game);
                        }
                    }
                }
            }
        }
        return true;
    }

    private void addReboundAbility(Card card, Ability source, Game game) {
        if (filter.match(card, game)) {
            boolean found = card.getAbilities(game).containsClass(ReboundAbility.class);
            if (!found) {
                Ability ability = new ReboundAbility();
                game.getState().addOtherAbility(card, ability);
            }
        }
    }
}
