package mage.cards.g;

import mage.MageInt;
import mage.MageObjectReference;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.TargetPermanent;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GadwickTheWizened extends CardImpl {

    private static final FilterSpell filter
            = new FilterSpell("a blue spell");
    private static final FilterPermanent filter2
            = new FilterNonlandPermanent("nonland permanent an opponent controls");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
        filter2.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public GadwickTheWizened(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{U}{U}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Gadwick, the Wizened enters the battlefield, draw X cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DrawCardSourceControllerEffect(GadwickTheWizenedValue.instance)
        ), new GadwickTheWizenedWatcher());

        // Whenever you cast a blue spell, tap target nonland permanent an opponent controls.
        Ability ability = new SpellCastControllerTriggeredAbility(new TapTargetEffect(), filter, false);
        ability.addTarget(new TargetPermanent(filter2));
        this.addAbility(ability);
    }

    private GadwickTheWizened(final GadwickTheWizened card) {
        super(card);
    }

    @Override
    public GadwickTheWizened copy() {
        return new GadwickTheWizened(this);
    }
}

enum GadwickTheWizenedValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        GadwickTheWizenedWatcher watcher = game.getState().getWatcher(GadwickTheWizenedWatcher.class);
        if (watcher == null) {
            return 0;
        }
        return watcher.getX(new MageObjectReference(sourceAbility.getSourceId(), game));
    }

    @Override
    public DynamicValue copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "";
    }
}

class GadwickTheWizenedWatcher extends Watcher {

    private final Map<MageObjectReference, Integer> xMap = new HashMap();

    GadwickTheWizenedWatcher() {
        super(WatcherScope.GAME);
    }

    private GadwickTheWizenedWatcher(final GadwickTheWizenedWatcher watcher) {
        super(watcher);
        this.xMap.putAll(watcher.xMap);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST) {
            return;
        }
        Spell spell = game.getSpellOrLKIStack(event.getTargetId());
        if (spell == null) {
            return;
        }
        xMap.put(new MageObjectReference(
                spell.getSourceId(), spell.getZoneChangeCounter(game) + 1, game
        ), spell.getSpellAbility().getManaCostsToPay().getX());
    }

    @Override
    public GadwickTheWizenedWatcher copy() {
        return new GadwickTheWizenedWatcher(this);
    }

    @Override
    public void reset() {
        super.reset();
        xMap.clear();
    }

    public int getX(MageObjectReference mageObjectReference) {
        return xMap.getOrDefault(mageObjectReference, 0);
    }
}
