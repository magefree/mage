package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.mana.AnyColorLandsProduceManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GondGate extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.GATE);

    public GondGate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.GATE);

        // Gates you control enter the battlefield untapped.
        this.addAbility(new SimpleStaticAbility(new GondGateEffect()));

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}: Add one mana of any color that a Gate you control could produce.
        this.addAbility(new AnyColorLandsProduceManaAbility(TargetController.YOU, true, filter));
    }

    private GondGate(final GondGate card) {
        super(card);
    }

    @Override
    public GondGate copy() {
        return new GondGate(this);
    }
}

class GondGateEffect extends ReplacementEffectImpl {

    GondGateEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Gates you control enter the battlefield untapped";
    }

    private GondGateEffect(final GondGateEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ((EntersTheBattlefieldEvent) event).getTarget().setTapped(false);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent targetObject = ((EntersTheBattlefieldEvent) event).getTarget();
        return targetObject != null
                && targetObject.isControlledBy(source.getControllerId())
                && targetObject.hasSubtype(SubType.GATE, game);
    }

    @Override
    public GondGateEffect copy() {
        return new GondGateEffect(this);
    }
}
