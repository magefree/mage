package mage.abilities.keyword;

import java.util.UUID;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SpellAbilityCastMode;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 * 702.33. Madness
 *
 * 702.33a. Madness is a keyword that represents two abilities.
 *
 * The first is a static ability that functions while the card with madness is
 * in a player's hand. The second is a triggered ability that functions when the
 * first ability is applied.
 *
 * "Madness [cost]" means "If a player would discard this card, that player
 * discards it, but may exile it instead of putting it into their graveyard" and
 * "When this card is exiled this way, its owner may cast it by paying [cost]
 * rather than paying its mana cost. If that player doesn't, he or she puts this
 * card into their graveyard.
 *
 * 702.33b. Casting a spell using its madness ability follows the rules for
 * paying alternative costs in rules 601.2b and 601.2e-g.
 *
 * SOI Changes: If you discard a card with madness, you exile it instead of
 * putting it into your graveyard. Note that the mandatory discard into exile is
 * a small change from previous rules. Before, you could discard a card with
 * madness into your graveyard and skip the whole madness thing. This may be
 * relevant with cards like Jace, Vryn's Prodigy.
 *
 * @author LevelX2
 */
public class MadnessAbility extends StaticAbility {

    private String rule;

    @SuppressWarnings("unchecked")
    public MadnessAbility(Card card, ManaCosts madnessCost) {
        super(Zone.HAND, new MadnessReplacementEffect((ManaCosts<ManaCost>) madnessCost));
        addSubAbility(new MadnessTriggeredAbility((ManaCosts<ManaCost>) madnessCost, getOriginalId()));
        rule = "Madness " + madnessCost.getText() + " <i>(If you discard this card, discard it into exile. When you do, cast it for its madness cost or put it into your graveyard.)</i>";
    }

    public MadnessAbility(final MadnessAbility ability) {
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

    public MadnessReplacementEffect(ManaCosts<ManaCost> madnessCost) {
        super(Duration.EndOfGame, Outcome.Benefit);
        staticText = "Madness " + madnessCost.getText() + " <i>(If you discard this card, you may cast it for its madness cost instead of putting it into your graveyard.)</i>";
    }

    public MadnessReplacementEffect(final MadnessReplacementEffect effect) {
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
        if (controller != null) {
            Card card = game.getCard(event.getTargetId());
            if (card != null) {
                if (controller.moveCardToExileWithInfo(card, source.getSourceId(), "Madness", source.getSourceId(), game, ((ZoneChangeEvent) event).getFromZone(), true)) {
                    game.applyEffects(); // needed to add Madness ability to cards (e.g. by Falkenrath Gorger)
                    GameEvent gameEvent = GameEvent.getEvent(GameEvent.EventType.MADNESS_CARD_EXILED, card.getId(), source.getOriginalId(), controller.getId());
                    game.fireEvent(gameEvent);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(source.getSourceId())
                && ((ZoneChangeEvent) event).getFromZone() == Zone.HAND && ((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD;
    }

}

/**
 * Checks for the MADNESS_CARD_EXILED event to ask the player if he wants to
 * cast the card by it's Madness costs. If not, the card goes to the graveyard.
 */
class MadnessTriggeredAbility extends TriggeredAbilityImpl {

    private final UUID madnessOriginalId;

    MadnessTriggeredAbility(ManaCosts<ManaCost> madnessCost, UUID madnessOriginalId) {
        super(Zone.EXILED, new MadnessCastEffect(madnessCost), true);
        this.madnessOriginalId = madnessOriginalId;
        this.setRuleVisible(false);
    }

    MadnessTriggeredAbility(final MadnessTriggeredAbility ability) {
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
        return event.getSourceId().equals(madnessOriginalId); // Check that the event was from the connected replacement effect
    }

    @Override
    public boolean resolve(Game game) {
        if (!super.resolve(game)) {
            Card card = game.getCard(getSourceId());
            if (card != null) {
                Player owner = game.getPlayer(card.getOwnerId());
                if (owner != null) {
                    // if cast was not successfull, the card is moved to graveyard
                    owner.moveCards(card, Zone.GRAVEYARD, this, game);
                }
            }
            return false;
        }
        return true;
    }

    @Override
    public String getRule() {
        return "When this card is exiled this way, " + super.getRule();
    }
}

class MadnessCastEffect extends OneShotEffect {

    private final ManaCosts<ManaCost> madnessCost;

    public MadnessCastEffect(ManaCosts<ManaCost> madnessCost) {
        super(Outcome.Benefit);
        this.madnessCost = madnessCost;
        staticText = "you may cast it by paying " + madnessCost.getText() + " instead of putting it into your graveyard";
    }

    public MadnessCastEffect(final MadnessCastEffect effect) {
        super(effect);
        this.madnessCost = effect.madnessCost;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player owner = null;
        Card card = game.getCard(source.getSourceId());
        if (card != null) {
            owner = game.getPlayer(card.getOwnerId());
        }
        if (owner != null && card != null
                && owner.chooseUse(outcome, "Cast " + card.getLogName() + " by madness?", source, game)) {

            // replace with the new cost
            SpellAbility castByMadness = card.getSpellAbility().copy();
            ManaCosts<ManaCost> costRef = castByMadness.getManaCostsToPay();
            castByMadness.setSpellAbilityType(SpellAbilityType.BASE_ALTERNATE);
            castByMadness.setSpellAbilityCastMode(SpellAbilityCastMode.MADNESS);
            costRef.clear();
            costRef.add(madnessCost);
            boolean result = owner.cast(castByMadness, game, false, new MageObjectReference(source.getSourceObject(game), game));
            return result;

        }
        return false;
    }

    @Override
    public MadnessCastEffect copy() {
        return new MadnessCastEffect(this);
    }
}

enum MadnessCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject madnessSpell = game.getLastKnownInformation(source.getSourceId(), Zone.STACK, source.getSourceObjectZoneChangeCounter() - 1);
        if (madnessSpell instanceof Spell) {
            if (((Spell) madnessSpell).getSpellAbility() != null) {
                return ((Spell) madnessSpell).getSpellAbility().getSpellAbilityCastMode() == SpellAbilityCastMode.MADNESS;
            }
        }
        return false;
    }

}
