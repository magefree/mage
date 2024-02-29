package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.PreventDamageToSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.util.GameLog;

import java.util.UUID;

/**
 * @author Cguy7777
 */
public final class TorrentOfLava extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature without flying");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public TorrentOfLava(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}{R}");

        // Torrent of Lava deals X damage to each creature without flying.
        this.getSpellAbility().addEffect(new DamageAllEffect(ManacostVariableValue.REGULAR, filter));

        // As long as Torrent of Lava is on the stack, each creature has
        // "{tap}: Prevent the next 1 damage that would be dealt to this creature by Torrent of Lava this turn."
        this.addAbility(new SimpleStaticAbility(Zone.STACK, new TorrentOfLavaGainAbilityEffect()));
    }

    private TorrentOfLava(final TorrentOfLava card) {
        super(card);
    }

    @Override
    public TorrentOfLava copy() {
        return new TorrentOfLava(this);
    }
}

class TorrentOfLavaGainAbilityEffect extends GainAbilityAllEffect {

    TorrentOfLavaGainAbilityEffect() {
        super(new SimpleActivatedAbility(
                        new TorrentOfLavaPreventionEffect(null, 0), new TapSourceCost()),
                Duration.Custom,
                StaticFilters.FILTER_PERMANENT_CREATURES);
        this.staticText = "As long as {this} is on the stack, " +
                "each creature has \"{T}: Prevent the next 1 damage that would be dealt to this creature by {this} this turn.\"";
    }

    private TorrentOfLavaGainAbilityEffect(final TorrentOfLavaGainAbilityEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(source.getSourceId());
        if (spell == null) {
            return false;
        }

        Effect effect = new TorrentOfLavaPreventionEffect(spell.getId(), spell.getZoneChangeCounter(game));
        // Display the id of the spell on the stack, not the card id
        String idName = spell.getName() + " [" + spell.getId().toString().substring(0, 3) + "]";
        effect.setText("Prevent the next 1 damage that would be dealt to {this} by "
                + GameLog.getColoredObjectIdNameForTooltip(spell.getColor(game), idName) + " this turn");

        ability = new SimpleActivatedAbility(effect, new TapSourceCost());
        return super.apply(game, source);
    }

    @Override
    public TorrentOfLavaGainAbilityEffect copy() {
        return new TorrentOfLavaGainAbilityEffect(this);
    }
}

class TorrentOfLavaPreventionEffect extends PreventDamageToSourceEffect {

    private final UUID preventDamageFromId;
    private final int zoneChangeCounter;

    TorrentOfLavaPreventionEffect(UUID preventDamageFromId, int zoneChangeCounter) {
        super(Duration.EndOfTurn, 1);
        this.preventDamageFromId = preventDamageFromId;
        this.zoneChangeCounter = zoneChangeCounter;
    }

    private TorrentOfLavaPreventionEffect(final TorrentOfLavaPreventionEffect effect) {
        super(effect);
        this.preventDamageFromId = effect.preventDamageFromId;
        this.zoneChangeCounter = effect.zoneChangeCounter;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!super.applies(event, source, game) || preventDamageFromId == null) {
            return false;
        }

        Spell spell = game.getStack().getSpell(event.getSourceId());
        if (spell == null) {
            return false;
        }

        return spell.getId().equals(preventDamageFromId) && spell.getZoneChangeCounter(game) == zoneChangeCounter;
    }

    @Override
    public TorrentOfLavaPreventionEffect copy() {
        return new TorrentOfLavaPreventionEffect(this);
    }
}
