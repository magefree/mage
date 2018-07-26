package mage.cards.t;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

/**
 *
 * @author TheElk801
 */
public final class TuvasaTheSunlit extends CardImpl {

    private static final FilterEnchantmentPermanent filter
            = new FilterEnchantmentPermanent("enchantment you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public TuvasaTheSunlit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Tuvasa the Sunlit gets +1/+1 for each enchantment you control.
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

    public TuvasaTheSunlit(final TuvasaTheSunlit card) {
        super(card);
    }

    @Override
    public TuvasaTheSunlit copy() {
        return new TuvasaTheSunlit(this);
    }
}

class TuvasaTheSunlitTriggeredAbility extends SpellCastControllerTriggeredAbility {

    private static final FilterSpell filter
            = new FilterSpell("an enchantment spell");

    static {
        filter.add(new CardTypePredicate(CardType.ENCHANTMENT));
    }

    public TuvasaTheSunlitTriggeredAbility() {
        super(new DrawCardSourceControllerEffect(1), filter, false);
        this.rule = "Whenever you cast your first enchantment spell each turn, draw a card.";
    }

    public TuvasaTheSunlitTriggeredAbility(final TuvasaTheSunlitTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!super.checkTrigger(event, game)) {
            return false;
        }
        TuvasaTheSunlitWatcher watcher = (TuvasaTheSunlitWatcher) game.getState().getWatchers().get(TuvasaTheSunlitWatcher.class.getSimpleName());
        MageObjectReference mor = watcher.getFirstEnchantmentThisTurn(this.getControllerId());
        return mor == null || mor.refersTo(event.getTargetId(), game);
    }

}

class TuvasaTheSunlitWatcher extends Watcher {

    private final Map<UUID, MageObjectReference> firstEnchantmentThisTurn = new HashMap();

    public TuvasaTheSunlitWatcher() {
        super(TuvasaTheSunlitWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public TuvasaTheSunlitWatcher(final TuvasaTheSunlitWatcher watcher) {
        super(watcher);
        this.firstEnchantmentThisTurn.putAll(watcher.firstEnchantmentThisTurn);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && spell.isEnchantment()) {
                firstEnchantmentThisTurn.putIfAbsent(
                        event.getPlayerId(),
                        new MageObjectReference(spell, game)
                );
            }
        }
    }

    @Override
    public void reset() {
        firstEnchantmentThisTurn.clear();
    }

    public MageObjectReference getFirstEnchantmentThisTurn(UUID playerId) {
        return firstEnchantmentThisTurn.get(playerId);
    }

    @Override
    public Watcher copy() {
        return new TuvasaTheSunlitWatcher(this);
    }
}
