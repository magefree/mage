package mage.cards.c;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.*;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author notgreat
 */
public final class CharredFoyerWarpedSpace extends RoomCard {

    public CharredFoyerWarpedSpace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, "{3}{R}", "{4}{R}{R}");


        // Charred Foyer
        // At the beginning of your upkeep, exile the top card of your library. You may play it this turn.
        this.getLeftHalfCard().addAbility(new BeginningOfUpkeepTriggeredAbility(new ExileTopXMayPlayUntilEffect(1, Duration.EndOfTurn)));

        // Warped Space
        // Once each turn, you may pay {0} rather than pay the mana cost for a spell you cast from exile.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(
                new WarpedSpaceAddAltCostEffect()));
    }

    private CharredFoyerWarpedSpace(final CharredFoyerWarpedSpace card) {
        super(card);
    }

    @Override
    public CharredFoyerWarpedSpace copy() {
        return new CharredFoyerWarpedSpace(this);
    }
}

//Based on TlincalliHunter
enum ExiledSpellCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source);
        if (object instanceof SplitCardHalf || object instanceof SpellOptionCard || object instanceof ModalDoubleFacedCardHalf) {
            UUID mainCardId = ((Card) object).getMainCard().getId();
            object = game.getObject(mainCardId);
        }
        if (object instanceof Spell) { // needed to check if it can be cast by alternate cost
            Spell spell = (Spell) object;
            return (Zone.EXILED.equals(spell.getFromZone()));
        }
        if (object instanceof Card) { // needed for checking what's playable
            Card card = (Card) object;
            return (game.getExile().getCard(card.getId(), game) != null);
        }
        return false;
    }
}

class WarpedSpaceAlternativeCost extends AlternativeCostSourceAbility {

    private boolean wasActivated;

    WarpedSpaceAlternativeCost() {
        super(new ManaCostsImpl<>("{0}"), ExiledSpellCondition.instance);
    }

    private WarpedSpaceAlternativeCost(final WarpedSpaceAlternativeCost ability) {
        super(ability);
        this.wasActivated = ability.wasActivated;
    }

    @Override
    public WarpedSpaceAlternativeCost copy() {
        return new WarpedSpaceAlternativeCost(this);
    }

    @Override
    public boolean activateAlternativeCosts(Ability ability, Game game) {
        if (!super.activateAlternativeCosts(ability, game)) {
            return false;
        }
        Permanent provider = game.getPermanent(getSourceId());
        if (provider != null) {
            game.getState().setValue(provider.getId().toString()
                    + provider.getZoneChangeCounter(game)
                    + provider.getTurnsOnBattlefield(), true);
        }
        return true;
    }
}

class WarpedSpaceAddAltCostEffect extends ContinuousEffectImpl {

    WarpedSpaceAddAltCostEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Once each turn, you may pay {0} rather than pay the mana cost for a creature spell you cast from exile.";
    }

    private WarpedSpaceAddAltCostEffect(final WarpedSpaceAddAltCostEffect effect) {
        super(effect);
    }

    @Override
    public WarpedSpaceAddAltCostEffect copy() {
        return new WarpedSpaceAddAltCostEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            if (sourcePermanent != null) {
                Boolean wasItUsed = (Boolean) game.getState().getValue(
                        sourcePermanent.getId().toString()
                                + sourcePermanent.getZoneChangeCounter(game)
                                + sourcePermanent.getTurnsOnBattlefield());
                // If we haven't used it yet this turn, give the option of using the zero alternative cost
                if (wasItUsed == null) {
                    WarpedSpaceAlternativeCost alternateCostAbility = new WarpedSpaceAlternativeCost();
                    alternateCostAbility.setSourceId(source.getSourceId());
                    controller.getAlternativeSourceCosts().add(alternateCostAbility);
                }
                // Return true even if we didn't add the alt cost. We still applied the effect
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}
