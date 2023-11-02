package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.ruleModifying.LegendRuleDoesntApplyEffect;
import mage.abilities.keyword.MyriadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class TheMasterMultiplied extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("creature tokens you control");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public TheMasterMultiplied(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TIME_LORD, SubType.ROGUE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Myriad
        this.addAbility(new MyriadAbility(false));

        // The "legend rule" doesn't apply to creature tokens you control.
        this.addAbility(new SimpleStaticAbility(new LegendRuleDoesntApplyEffect(filter)));

        // Triggered abilities you control can't cause you to sacrifice or exile creature tokens you control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new TheMasterMultipliedEffect()));
    }

    private TheMasterMultiplied(final TheMasterMultiplied card) {
        super(card);
    }

    @Override
    public TheMasterMultiplied copy() {
        return new TheMasterMultiplied(this);
    }
}

class TheMasterMultipliedEffect extends ContinuousRuleModifyingEffectImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("creature tokens you control");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public TheMasterMultipliedEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Triggered abilities you control can't cause you to sacrifice or exile creature tokens you control";
    }

    private TheMasterMultipliedEffect(final TheMasterMultipliedEffect effect) {
        super(effect);
    }

    @Override
    public TheMasterMultipliedEffect copy() {
        return new TheMasterMultipliedEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SACRIFICE_PERMANENT
                || event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        UUID eventSourceControllerId = game.getControllerId(event.getSourceId());
        Permanent permanent = game.getPermanent(event.getTargetId());

        StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
        if (stackObject == null) {
            return false;
        }
        Ability stackAbility = stackObject.getStackAbility();

        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (!zEvent.getToZone().equals(Zone.EXILED)) {
                return false;
            }
        }

        return controller != null && permanent != null
                && filter.match(permanent, source.getControllerId(), source, game)
                && stackAbility instanceof TriggeredAbility
                && source.getControllerId().equals(eventSourceControllerId);
    }
}
