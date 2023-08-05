package mage.cards.d;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceIsSpellCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class DarksteelMonolith extends CardImpl {

    public DarksteelMonolith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{8}");

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // Once each turn, you may pay {0} rather than pay the mana cost for a colorless spell that you cast from your hand.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new DarksteelMonolithAddAltCostEffect()
        ));
    }

    private DarksteelMonolith(final DarksteelMonolith card) {
        super(card);
    }

    @Override
    public DarksteelMonolith copy() {
        return new DarksteelMonolith(this);
    }
}

enum IsBeingCastFromHandCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source);
        if (object instanceof SplitCardHalf || object instanceof AdventureCardSpell || object instanceof ModalDoubleFacedCardHalf) {
            UUID mainCardId = ((Card) object).getMainCard().getId();
            object = game.getObject(mainCardId);
        }
        if (object instanceof Spell) { // needed to check if it can be cast by alternate cost
            Spell spell = (Spell) object;
            return Zone.HAND.equals(spell.getFromZone());
        }
        if (object instanceof Card) { // needed for checking what's playable
            Card card = (Card) object;
            return game.getPlayer(card.getOwnerId()).getHand().get(card.getId(), game) != null;
        }
        return false;
    }
}

class DarksteelMonolithAlternativeCost extends AlternativeCostSourceAbility {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(ColorlessPredicate.instance);
    }

    private boolean wasActivated;

    DarksteelMonolithAlternativeCost() {
        super(new ManaCostsImpl<>("{0}"), new CompoundCondition(SourceIsSpellCondition.instance, IsBeingCastFromHandCondition.instance), null, filter, true);
    }

    private DarksteelMonolithAlternativeCost(final DarksteelMonolithAlternativeCost ability) {
        super(ability);
        this.wasActivated = ability.wasActivated;
    }

    @Override
    public DarksteelMonolithAlternativeCost copy() {
        return new DarksteelMonolithAlternativeCost(this);
    }

    @Override
    public boolean askToActivateAlternativeCosts(Ability ability, Game game) {
        Player controller = game.getPlayer(ability.getControllerId());
        Permanent monolith = game.getPermanent(getSourceId());
        if (controller != null
                && monolith != null) {
            if (controller.chooseUse(Outcome.Neutral, "Use "
                    + monolith.getLogName() + " to pay the alternative cost?", ability, game)) {
                wasActivated = super.askToActivateAlternativeCosts(ability, game);
                if (wasActivated) {
                    game.getState().setValue(monolith.getId().toString()
                            + monolith.getZoneChangeCounter(game)
                            + monolith.getTurnsOnBattlefield(), true);
                }
            }
        }
        return wasActivated;
    }
}

class DarksteelMonolithAddAltCostEffect extends ContinuousEffectImpl {

    public DarksteelMonolithAddAltCostEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Once each turn, you may pay {0} rather than pay the mana cost for a colorless spell you cast from your hand.";
    }

    public DarksteelMonolithAddAltCostEffect(final DarksteelMonolithAddAltCostEffect effect) {
        super(effect);
    }

    @Override
    public DarksteelMonolithAddAltCostEffect copy() {
        return new DarksteelMonolithAddAltCostEffect(this);
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
                    DarksteelMonolithAlternativeCost alternateCostAbility = new DarksteelMonolithAlternativeCost();
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
