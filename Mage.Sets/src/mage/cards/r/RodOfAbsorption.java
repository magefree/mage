package mage.cards.r;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RodOfAbsorption extends CardImpl {

    public RodOfAbsorption(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}");

        // Whenever a player casts an instant or sorcery spell, exile it instead of putting it into a graveyard as it resolves.
        this.addAbility(new RodOfAbsorptionTriggeredAbility());

        // {X}, {T}, Sacrifice Rod of Absorption: You may cast any number of spells from among cards exiled with Rod of Absorption with total mana value X or less without paying their mana costs.
        Ability ability = new SimpleActivatedAbility(new RodOfAbsorptionCastEffect(), new ManaCostsImpl<>("{X}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private RodOfAbsorption(final RodOfAbsorption card) {
        super(card);
    }

    @Override
    public RodOfAbsorption copy() {
        return new RodOfAbsorption(this);
    }
}

class RodOfAbsorptionTriggeredAbility extends TriggeredAbilityImpl {

    RodOfAbsorptionTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
    }

    private RodOfAbsorptionTriggeredAbility(final RodOfAbsorptionTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RodOfAbsorptionTriggeredAbility copy() {
        return new RodOfAbsorptionTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell == null || !spell.isInstantOrSorcery(game)) {
            return false;
        }
        this.getEffects().clear();
        this.addEffect(new RodOfAbsorptionExileEffect(spell, game));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever a player casts an instant or sorcery spell, " +
                "exile it instead of putting it into a graveyard as it resolves.";
    }
}

class RodOfAbsorptionExileEffect extends ReplacementEffectImpl {

    private final MageObjectReference mor;

    RodOfAbsorptionExileEffect(Spell spell, Game game) {
        super(Duration.WhileOnStack, Outcome.Benefit);
        this.mor = new MageObjectReference(spell, game);
    }

    private RodOfAbsorptionExileEffect(final RodOfAbsorptionExileEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Spell sourceSpell = game.getStack().getSpell(event.getTargetId());
        if (sourceSpell == null || sourceSpell.isCopy()) {
            return false;
        }
        Player player = game.getPlayer(sourceSpell.getOwnerId());
        if (player == null) {
            return false;
        }
        player.moveCardsToExile(
                sourceSpell, source, game, false,
                CardUtil.getExileZoneId(game, source),
                CardUtil.getSourceName(game, source)
        );
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = ((ZoneChangeEvent) event);
        if (zEvent.getFromZone() != Zone.STACK
                || zEvent.getToZone() != Zone.GRAVEYARD
                || event.getSourceId() == null
                || !event.getSourceId().equals(event.getTargetId())
                || mor.getZoneChangeCounter() != game.getState().getZoneChangeCounter(event.getSourceId())) {
            return false;
        }
        Spell spell = game.getStack().getSpell(mor.getSourceId());
        return spell != null && spell.isInstantOrSorcery(game);
    }

    @Override
    public RodOfAbsorptionExileEffect copy() {
        return new RodOfAbsorptionExileEffect(this);
    }
}

class RodOfAbsorptionCastEffect extends OneShotEffect {

    RodOfAbsorptionCastEffect() {
        super(Outcome.Benefit);
        staticText = "you may cast any number of spells from among cards exiled with " +
                "{this} with total mana value X or less without paying their mana costs";
    }

    private RodOfAbsorptionCastEffect(final RodOfAbsorptionCastEffect effect) {
        super(effect);
    }

    @Override
    public RodOfAbsorptionCastEffect copy() {
        return new RodOfAbsorptionCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Cards cards = new CardsImpl(game.getExile().getExileZone(CardUtil.getExileZoneId(game, source)));
        if (player == null || cards.isEmpty()) {
            return false;
        }
        CardUtil.castMultipleWithAttributeForFree(
                player, source, game, cards, StaticFilters.FILTER_CARD, Integer.MAX_VALUE,
                new RodOfAbsorptionTracker(source.getManaCostsToPay().getX())
        );
        return true;
    }
}

class RodOfAbsorptionTracker implements CardUtil.SpellCastTracker {

    private final int xValue;
    private int totalManaValue = 0;

    RodOfAbsorptionTracker(int xValue) {
        this.xValue = xValue;
    }

    @Override
    public boolean checkCard(Card card, Game game) {
        return card.getManaValue() + totalManaValue <= xValue;
    }

    @Override
    public void addCard(Card card, Ability source, Game game) {
        totalManaValue += card.getManaValue();
    }
}
