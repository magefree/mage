
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author nantuko
 */
public final class DearlyDeparted extends CardImpl {

    public DearlyDeparted(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        this.addAbility(FlyingAbility.getInstance());

        // As long as Dearly Departed is in your graveyard, each Human creature you control enters the battlefield with an additional +1/+1 counter on it.
        this.addAbility(new SimpleStaticAbility(Zone.GRAVEYARD, new DearlyDepartedEntersBattlefieldEffect()));
    }

    private DearlyDeparted(final DearlyDeparted card) {
        super(card);
    }

    @Override
    public DearlyDeparted copy() {
        return new DearlyDeparted(this);
    }
}

class DearlyDepartedEntersBattlefieldEffect extends ReplacementEffectImpl {

    public DearlyDepartedEntersBattlefieldEffect() {
        super(Duration.WhileInGraveyard, Outcome.BoostCreature);
        staticText = "As long as {this} is in your graveyard, each Human creature you control enters the battlefield with an additional +1/+1 counter on it";
    }

    private DearlyDepartedEntersBattlefieldEffect(final DearlyDepartedEntersBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        if (permanent != null && permanent.isControlledBy(source.getControllerId()) && permanent.hasSubtype(SubType.HUMAN, game)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent target = ((EntersTheBattlefieldEvent) event).getTarget();
        if (target != null) {
            target.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game, event.getAppliedEffects());
        }
        return false;
    }

    @Override
    public DearlyDepartedEntersBattlefieldEffect copy() {
        return new DearlyDepartedEntersBattlefieldEffect(this);
    }

}
