package mage.cards.t;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

/**
 *
 * @author TheElk801
 */
public final class TuvasaTheSunlit extends CardImpl {

    public TuvasaTheSunlit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Tuvasa the Sunlit gets +1/+1 for each enchantment you control.
        FilterEnchantmentPermanent filter
                = new FilterEnchantmentPermanent("enchantment you control");
        filter.add(TargetController.YOU.getControllerPredicate());
        DynamicValue value
                = new PermanentsOnBattlefieldCount(new FilterPermanent(filter));
        Ability ability = new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new BoostSourceEffect(
                        value, value, Duration.WhileOnBattlefield
                ).setText("{this} gets +1/+1 for each enchantment you control")
        );
        this.addAbility(ability);

        // Whenever you cast your first enchantment spell each turn, draw a card.
        this.addAbility(
                new TuvasaTheSunlitTriggeredAbility(),
                new TuvasaTheSunlitWatcher()
        );
    }

    private TuvasaTheSunlit(final TuvasaTheSunlit card) {
        super(card);
    }

    @Override
    public TuvasaTheSunlit copy() {
        return new TuvasaTheSunlit(this);
    }
}

class TuvasaTheSunlitTriggeredAbility extends SpellCastControllerTriggeredAbility {

    public TuvasaTheSunlitTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1),
                (FilterSpell) new FilterSpell("an enchantment spell").add(CardType.ENCHANTMENT.getPredicate()), false, true);
    }

    public TuvasaTheSunlitTriggeredAbility(final TuvasaTheSunlitTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            TuvasaTheSunlitWatcher watcher = game.getState().getWatcher(
                    TuvasaTheSunlitWatcher.class
            );
            return watcher != null && event.getTargetId().equals(watcher.getFirstEnchantmentThisTurn(this.getControllerId()));
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast your first enchantment spell each turn, draw a card.";
    }

    @Override
    public TuvasaTheSunlitTriggeredAbility copy() {
        return new TuvasaTheSunlitTriggeredAbility(this);
    }
}

class TuvasaTheSunlitWatcher extends Watcher {

    private final Map<UUID, UUID> firstEnchantmentThisTurn = new HashMap<>();

    public TuvasaTheSunlitWatcher() {
        super( WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && spell.isEnchantment(game)) {
                firstEnchantmentThisTurn.putIfAbsent(
                        event.getPlayerId(),
                        spell.getId()
                );
            }
        }
    }

    @Override
    public void reset() {
        firstEnchantmentThisTurn.clear();
    }

    public UUID getFirstEnchantmentThisTurn(UUID playerId) {
        return firstEnchantmentThisTurn.get(playerId);
    }
}
