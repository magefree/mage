package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Grath
 */
public final class TlincalliHunter extends AdventureCard {

    public TlincalliHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{5}{G}{G}", "Retrieve Prey", "{1}{G}");
        
        this.subtype.add(SubType.SCORPION);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Once each turn, you may pay {0} rather than pay the mana cost for a creature spell you cast from exile.
        this.addAbility(new SimpleStaticAbility(
                new TlincalliHunterAddAltCostEffect()));

        // Retrieve Prey
        // Exile target creature card from your graveyard. Until the end of your next turn, you may cast that card.
        this.getSpellCard().getSpellAbility().addEffect(new RetrievePreyEffect());
        this.getSpellCard().getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.finalizeAdventure();
    }

    private TlincalliHunter(final TlincalliHunter card) {
        super(card);
    }

    @Override
    public TlincalliHunter copy() {
        return new TlincalliHunter(this);
    }
}

enum ExiledCreatureSpellCondition implements Condition {
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
            return (Zone.EXILED.equals(spell.getFromZone()) && spell.isCreature());
        }
        if (object instanceof Card) { // needed for checking what's playable
            Card card = (Card) object;
            return (game.getExile().getCard(card.getId(), game) != null && card.isCreature());
        }
        return false;
    }
}

class TlincalliHunterAlternativeCost extends AlternativeCostSourceAbility {

    private boolean wasActivated;

    TlincalliHunterAlternativeCost() {
        super(new ManaCostsImpl<>("{0}"), ExiledCreatureSpellCondition.instance);
    }

    private TlincalliHunterAlternativeCost(final TlincalliHunterAlternativeCost ability) {
        super(ability);
        this.wasActivated = ability.wasActivated;
    }

    @Override
    public TlincalliHunterAlternativeCost copy() {
        return new TlincalliHunterAlternativeCost(this);
    }

    @Override
    public boolean activateAlternativeCosts(Ability ability, Game game) {
        if (!super.activateAlternativeCosts(ability, game)) {
            return false;
        }
        Permanent hunter = game.getPermanent(getSourceId());
        if (hunter != null) {
            game.getState().setValue(hunter.getId().toString()
                    + hunter.getZoneChangeCounter(game)
                    + hunter.getTurnsOnBattlefield(), true);
        }
        return true;
    }
}

class TlincalliHunterAddAltCostEffect extends ContinuousEffectImpl {

    TlincalliHunterAddAltCostEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Once each turn, you may pay {0} rather than pay the mana cost for a creature spell you cast from exile.";
    }

    private TlincalliHunterAddAltCostEffect(final TlincalliHunterAddAltCostEffect effect) {
        super(effect);
    }

    @Override
    public TlincalliHunterAddAltCostEffect copy() {
        return new TlincalliHunterAddAltCostEffect(this);
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
                    TlincalliHunterAlternativeCost alternateCostAbility = new TlincalliHunterAlternativeCost();
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

class RetrievePreyEffect extends OneShotEffect {

    RetrievePreyEffect() {
        super(Outcome.Benefit);
        staticText = "exile target creature card from your graveyard. Until the end of your next turn, you may cast " +
                "that card";
    }

    private RetrievePreyEffect(final RetrievePreyEffect effect) {
        super(effect);
    }

    @Override
    public RetrievePreyEffect copy() {
        return new RetrievePreyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getFirstTarget());
        if (card == null) {
            return false;
        }
        PlayFromNotOwnHandZoneTargetEffect.exileAndPlayFromExile(
                game, source, card, TargetController.YOU,
                Duration.UntilEndOfYourNextTurn,
                false, false, true
        );
        return true;
    }
}
