package mage.cards.r;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffect;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Exile;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

public final class RodOfAbsorption extends CardImpl {
    private static int exileZoneNumber = 0;
    private final String exileZoneName;

    public String getExileZoneName() { return exileZoneName; }

    private static final FilterCard filter = new FilterCard("instant and sorcery spells");

    static { filter.add(Predicates.or(CardType.INSTANT.getPredicate(), CardType.SORCERY.getPredicate())); }

    public RodOfAbsorption(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{B}");

        this.color.setBlue(true);

        exileZoneName = "Rod of Absorption " + ++exileZoneNumber;

        // Whenever a player casts an instant or sorcery spell,
        // exile it instead of putting it into a graveyard as it resolves.
        this.addAbility(new RodOfAbsorptionExileTriggeredAbility());

        // X, P, Sacrifice Rod of Absorption: You may cast any number of spells from among cards exiled with Rod of
        //Absorption with total mana value X or
        //less without paying their mana costs.
        Ability tapSackAbility = new SimpleActivatedAbility(
                new RodOfAbsorptionPlayFromExileEffect(),
                new ManaCostsImpl<>("{X}"));
        tapSackAbility.addCost(new TapSourceCost());
        tapSackAbility.addCost(new SacrificeSourceCost());

    }

    private RodOfAbsorption(final RodOfAbsorption card) {
        super(card);
        exileZoneName = "Rod of Absorption " + exileZoneNumber;
    }

    @Override
    public Card copy() { return new RodOfAbsorption(this); }

    // Based on:
    // Feather The Redeemed  -> Triggered ability structure
    // Share the Spoils      -> Unique exile zones
    // Soulfire Grand Master -> Replacement effect
    class RodOfAbsorptionExileTriggeredAbility extends TriggeredAbilityImpl {

        RodOfAbsorptionExileTriggeredAbility() {
            super(Zone.BATTLEFIELD, null, false);
        }

        private RodOfAbsorptionExileTriggeredAbility(final RodOfAbsorptionExileTriggeredAbility ability) {
            super(ability);
        }

        @Override
        public boolean checkEventType(GameEvent event, Game game) {
            return event.getType() == GameEvent.EventType.SPELL_CAST;
        }

        @Override
        public boolean checkTrigger(GameEvent event, Game game) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell == null) { return false; }

            // Only Sorcery or Instant Spells
            if (!spell.isInstantOrSorcery(game)) { return false; }

            this.addEffect(new RodOfAbsorptionMoveToExileReplacementEffect(spell.getId()));

            return true;
        }

        @Override
        public RodOfAbsorptionExileTriggeredAbility copy() { return new RodOfAbsorptionExileTriggeredAbility(this); }

        @Override
        public String getRule() {
            return "Whenever a player casts an instant or sorcery spell, " +
                    "exile it instead of putting it into a graveyard as it resolves.";
        }
    }

    class RodOfAbsorptionMoveToExileReplacementEffect extends ReplacementEffectImpl {
        private final UUID spellToMoveId;

        RodOfAbsorptionMoveToExileReplacementEffect(UUID spellToMoveId) {
            // TODO: Is this a correct Outcome choice?
            super(Duration.Custom, Outcome.Exile);
            this.spellToMoveId = spellToMoveId;
            this.staticText = "Whenever a player casts an instant or sorcery spell, " +
                    "exile it instead of putting it into a graveyard as it resolves.";
        }

        private RodOfAbsorptionMoveToExileReplacementEffect(final RodOfAbsorptionMoveToExileReplacementEffect effect) {
            super(effect);
            this.spellToMoveId = effect.spellToMoveId;
        }

        @Override
        public boolean replaceEvent(GameEvent event, Ability source, Game game) {
            Spell sourceSpell = game.getStack().getSpell(event.getTargetId());
            // TODO: How to check if sourceSpell resolves properly?
            if (sourceSpell == null || sourceSpell.isCopy() || sourceSpell.isCountered()) { return false; }

            Player player = game.getPlayer(sourceSpell.getOwnerId());
            if (player == null) { return false; }

            Spell spellToMove = game.getStack().getSpell(spellToMoveId);
            if (spellToMove == null) { return false; }

            Card cardToMove = spellToMove.getCard();
            if (cardToMove == null) { return false; }
            // TODO: Why is this here?
            if (game.getState().getZone(spellToMoveId) != Zone.STACK) { return false; }

            // Move to exile
            player.moveCards(cardToMove, Zone.EXILED, source, game);

            // Create/Get specific zone within exile for this Rod of Absorption
            ExileZone exileZone;

            // TODO: Better way to handle this?
            // The first time this replacement effect runs there won't be a zone, so we have to make one.
            // Everytime this gets called after the first, it should use the new zone.
            ExileZone testExileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(exileZoneName, game));
            if (testExileZone == null) {
                // Zone not yet created, create a new one
                exileZone = game.getExile().createZone(CardUtil.getExileZoneId(exileZoneName, game), exileZoneName);
            } else {
                // Zone exists, use it
                exileZone = testExileZone;
            }

            // Move to specific zone of exile
            game.getExile().moveToAnotherZone(cardToMove, game, exileZone);
            return true;
        }

        // TODO: I don't understand why it's .ZONE_CHANGE
        @Override
        public boolean checksEventType(GameEvent event, Game game) {
            return event.getType() == GameEvent.EventType.ZONE_CHANGE;
        }

        @Override
        public boolean applies(GameEvent event, Ability source, Game game) {
            if (!(event instanceof ZoneChangeEvent)) { return false; }
            if (event.getSourceId() == null) { return false; }

            ZoneChangeEvent zEvent = ((ZoneChangeEvent) event);

            if (!(zEvent.getFromZone() == Zone.STACK && zEvent.getToZone() == Zone.GRAVEYARD)) { return false; }

            // TODO: From Reather the Redeemed, I don't know if it's necessary
            Spell spell = game.getStack().getSpell(spellToMoveId);
            if (spell == null) { return false; }
            if (spell.getZoneChangeCounter(game) != game.getState().getZoneChangeCounter(event.getSourceId())) {
                return false;
            }
            return true;
        }

        @Override
        public RodOfAbsorptionMoveToExileReplacementEffect copy() {
            return new RodOfAbsorptionMoveToExileReplacementEffect(this);
        }
    }

    class RodOfAbsorptionPlayFromExileEffect extends AsThoughEffectImpl {

        RodOfAbsorptionPlayFromExileEffect() {
            super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnStack, Outcome.PlayForFree);
            this.staticText = "You may cast any number of spells from among cards exiled with Rod of Absorption " +
                    "with total mana value X or less without paying their mana costs.";
        }

        private RodOfAbsorptionPlayFromExileEffect(final RodOfAbsorptionPlayFromExileEffect effect) {
            super(effect);
            this.staticText = effect.staticText;
        }

        @Override
        public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
            return false;
        }

        @Override
        public boolean apply(Game game, Ability source) {
            return false;
        }

        @Override
        public AsThoughEffect copy() { return new RodOfAbsorptionPlayFromExileEffect(this); }
    }
}
