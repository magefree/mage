
package mage.cards.p;

import java.util.UUID;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ManaEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author L_J
 */
public final class PulseOfLlanowar extends CardImpl {

    public PulseOfLlanowar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        // If a basic land you control is tapped for mana, it produces mana of a color of your choice instead of any other type.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PulseOfLlanowarReplacementEffect()));
    }

    public PulseOfLlanowar(final PulseOfLlanowar card) {
        super(card);
    }

    @Override
    public PulseOfLlanowar copy() {
        return new PulseOfLlanowar(this);
    }
}

class PulseOfLlanowarReplacementEffect extends ReplacementEffectImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent();
    static {
        filter.add(new SupertypePredicate(SuperType.BASIC));
    }

    PulseOfLlanowarReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "If a basic land you control is tapped for mana, it produces mana of a color of your choice instead of any other type";
    }

    PulseOfLlanowarReplacementEffect(final PulseOfLlanowarReplacementEffect effect) {
        super(effect);
    }

    @Override
    public PulseOfLlanowarReplacementEffect copy() {
        return new PulseOfLlanowarReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ManaEvent manaEvent = (ManaEvent) event;
        Mana mana = manaEvent.getMana();
        new AddManaOfAnyColorEffect(mana.count()).apply(game,source);
        mana.setToMana(new Mana(0,0,0,0,0,0,0,0));
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        MageObject mageObject = game.getObject(event.getSourceId());
        if (mageObject != null && mageObject.isLand()) {
            Permanent land = game.getPermanent(event.getSourceId());
            return land != null && filter.match(land, game);
        }
        return false;
    }
}
