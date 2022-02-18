package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.*;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.watchers.common.DamagedByControlledWatcher;

/**
 *
 * @author weirddan455
 */
public final class EtchingOfKumano extends CardImpl {

    public EtchingOfKumano(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setRed(true);
        this.nightCard = true;

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // If a creature dealt damage this turn by a source you controlled would die, exile it instead.
        this.addAbility(new SimpleStaticAbility(new EtchingOfKumanoReplacementEffect()), new DamagedByControlledWatcher());
    }

    private EtchingOfKumano(final EtchingOfKumano card) {
        super(card);
    }

    @Override
    public EtchingOfKumano copy() {
        return new EtchingOfKumano(this);
    }
}

class EtchingOfKumanoReplacementEffect extends ReplacementEffectImpl {

    public EtchingOfKumanoReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        this.staticText = "If a creature dealt damage this turn by a source you controlled would die, exile it instead";
    }

    private EtchingOfKumanoReplacementEffect(final EtchingOfKumanoReplacementEffect effect) {
        super(effect);
    }

    @Override
    public EtchingOfKumanoReplacementEffect copy() {
        return new EtchingOfKumanoReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ((ZoneChangeEvent) event).setToZone(Zone.EXILED);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zce = (ZoneChangeEvent) event;
        if (zce.isDiesEvent()) {
            DamagedByControlledWatcher watcher = game.getState().getWatcher(DamagedByControlledWatcher.class, source.getControllerId());
            if (watcher != null) {
                return watcher.wasDamaged(zce.getTarget(), game);
            }
        }
        return false;
    }
}
