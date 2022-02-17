package mage.abilities.keyword;

import mage.MageObject;
import mage.abilities.*;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.MadnessCardExiledEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.stack.Spell;
import mage.players.Player;

import java.util.UUID;
import mage.ApprovingObject;

/**
 * 702.33. Madness
 * <p>
 * 702.33a. Madness is a keyword that represents two abilities.
 * <p>
 * The first is a static ability that functions while the card with madness is
 * in a player's hand. The second is a triggered ability that functions when the
 * first ability is applied.
 * <p>
 * "Madness [cost]" means "If a player would discard this card, that player
 * discards it, but may exile it instead of putting it into their graveyard" and
 * "When this card is exiled this way, its owner may cast it by paying [cost]
 * rather than paying its mana cost. If that player doesn't, they put this
 * card into their graveyard.
 * <p>
 * 702.33b. Casting a spell using its madness ability follows the rules for
 * paying alternative costs in rules 601.2b and 601.2e-g.
 * <p>
 * SOI Changes: If you discard a card with madness, you exile it instead of
 * putting it into your graveyard. Note that the mandatory discard into exile is
 * a small change from previous rules. Before, you could discard a card with
 * madness into your graveyard and skip the whole madness thing. This may be
 * relevant with cards like Jace, Vryn's Prodigy.
 *
 * @author LevelX2
 */
public class MadnessAbility extends StaticAbility {

    private final String rule;

    public MadnessAbility(Card card, ManaCosts<ManaCost> madnessCost) {
        this(card, madnessCost, 0);
    }

    public MadnessAbility(Card card, ManaCosts<ManaCost> madnessCost, int lifeCost) {
        super(Zone.HAND, new MadnessReplacementEffect(madnessCost, lifeCost));
        addSubAbility(new MadnessTriggeredAbility(madnessCost, lifeCost, getOriginalId()));

        String costText;

        if (lifeCost > 0) {
            costText = "Madness—" + madnessCost.getText() + ", Pay " + lifeCost + " life.";
        } else {
            costText = "Madness " + madnessCost.getText();
        }

        this.rule = costText + " <i>(If you discard this card, discard it into exile. " +
                "When you do, cast it for its madness cost or put it into your graveyard.)</i>";
    }


    private MadnessAbility(final MadnessAbility ability) {
        super(ability);
        this.rule = ability.rule;
    }

    @Override
    public MadnessAbility copy() {
        return new MadnessAbility(this);
    }

    public static Condition getCondition() {
        return MadnessCondition.instance;
    }

    @Override
    public String getRule() {
        return rule;
    }
}

class MadnessReplacementEffect extends ReplacementEffectImpl {

    public MadnessReplacementEffect(ManaCosts<ManaCost> madnessCost, int lifeCost) {
        super(Duration.EndOfGame, Outcome.Benefit);

        String costText;

        if (lifeCost > 0) {
            costText = "Madness—" + madnessCost.getText() + ", Pay " + lifeCost + " life.";
        } else {
            costText = "Madness " + madnessCost.getText();
        }

        staticText = costText + " <i>(If you discard this card, you may cast it for its madness cost instead of putting it into your graveyard.)</i>";
    }

    private MadnessReplacementEffect(final MadnessReplacementEffect effect) {
        super(effect);
    }

    @Override
    public MadnessReplacementEffect copy() {
        return new MadnessReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) { return false; }

        Card card = game.getCard(event.getTargetId());
        if (card == null) { return false; }

        // TODO, deal with deprecated call
        if (controller.moveCardToExileWithInfo(card, source.getSourceId(), "Madness", source, game, ((ZoneChangeEvent) event).getFromZone(), true)) {
            game.applyEffects(); // needed to add Madness ability to cards (e.g. by Falkenrath Gorger)
            GameEvent gameEvent = new MadnessCardExiledEvent(card.getId(), source, controller.getId());
            game.fireEvent(gameEvent);
        }

        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(source.getSourceId())
                && ((ZoneChangeEvent) event).getFromZone() == Zone.HAND
                && ((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD;
    }

}

/**
 * Checks for the MADNESS_CARD_EXILED event to ask the player if they want to
 * cast the card by it's Madness costs. If not, the card goes to the graveyard.
 */
class MadnessTriggeredAbility extends TriggeredAbilityImpl {

    private final UUID madnessOriginalId;

    MadnessTriggeredAbility(ManaCosts<ManaCost> madnessCost, int lifeCost, UUID madnessOriginalId) {
        super(Zone.EXILED, new MadnessCastEffect(madnessCost, lifeCost), true);
        this.madnessOriginalId = madnessOriginalId;
        this.setRuleVisible(false);
    }

    private MadnessTriggeredAbility(final MadnessTriggeredAbility ability) {
        super(ability);
        this.madnessOriginalId = ability.madnessOriginalId;
    }

    @Override
    public MadnessTriggeredAbility copy() {
        return new MadnessTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.MADNESS_CARD_EXILED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // Check that the event was from the connected replacement effect
        return event.getSourceId().equals(madnessOriginalId);
    }

    @Override
    public boolean resolve(Game game) {
        if (super.resolve(game)) { return true; }

        Card card = game.getCard(getSourceId());
        if (card == null) { return false; }

        Player owner = game.getPlayer(card.getOwnerId());
        if (owner == null) { return false; }

        // if cast was not successfull, the card is moved to graveyard
        owner.moveCards(card, Zone.GRAVEYARD, this, game);
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "When this card is exiled this way, " ;
    }
}

class MadnessCastEffect extends OneShotEffect {

    private final ManaCosts<ManaCost> madnessCost;
    private final int lifeCost;

    public MadnessCastEffect(ManaCosts<ManaCost> madnessCost, int lifeCost) {
        super(Outcome.Benefit);
        this.madnessCost = madnessCost;
        this.lifeCost = lifeCost;

        String costText;

        if (lifeCost > 0) {
            costText = madnessCost.getText() + " and " + lifeCost + " life";
        } else {
            costText = madnessCost.getText();
        }

        staticText = "you may cast it by paying " + costText + " instead of putting it into your graveyard";
    }

    private MadnessCastEffect(final MadnessCastEffect effect) {
        super(effect);
        this.madnessCost = effect.madnessCost;
        this.lifeCost = effect.lifeCost;
    }

    @Override
    public MadnessCastEffect copy() { return new MadnessCastEffect(this); }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getSourceId());
        if (card == null) { return false; }

        Player owner = game.getPlayer(card.getOwnerId());
        if (owner == null) { return false; }

        // Replace with the new cost
        SpellAbility castByMadness = card.getSpellAbility().copy();
        ManaCosts<ManaCost> costRef = castByMadness.getManaCostsToPay();
        castByMadness.setSpellAbilityType(SpellAbilityType.BASE_ALTERNATE);
        castByMadness.setSpellAbilityCastMode(SpellAbilityCastMode.MADNESS);
        castByMadness.getCosts().clear();
        castByMadness.addCost(new PayLifeCost(this.lifeCost));
        costRef.clear();
        costRef.add(madnessCost);

        return owner.cast(castByMadness, game, false, new ApprovingObject(source, game));
    }
}

enum MadnessCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject madnessSpell = game.getLastKnownInformation(source.getSourceId(), Zone.STACK, source.getSourceObjectZoneChangeCounter() - 1);
        if (!(madnessSpell instanceof Spell)) { return false; }

        if (((Spell) madnessSpell).getSpellAbility() == null) { return false; }

        return ((Spell) madnessSpell).getSpellAbility().getSpellAbilityCastMode() == SpellAbilityCastMode.MADNESS;
    }
}
