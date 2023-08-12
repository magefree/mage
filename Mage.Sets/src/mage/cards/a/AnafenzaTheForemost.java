
package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class AnafenzaTheForemost extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another target tapped creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TappedPredicate.TAPPED);
    }

    public AnafenzaTheForemost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Anafenza, the Foremost attacks, put a +1/+1 counter on another target tapped creature you control.
        Ability ability = new AttacksTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), false);
        ability.addTarget(new TargetControlledCreaturePermanent(filter));
        this.addAbility(ability);

        // If a nontoken creature an opponent owns would die or a creature card not on the battlefield would be put into an opponent's graveyard, exile that card instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AnafenzaTheForemostEffect()));
    }

    private AnafenzaTheForemost(final AnafenzaTheForemost card) {
        super(card);
    }

    @Override
    public AnafenzaTheForemost copy() {
        return new AnafenzaTheForemost(this);
    }
}

class AnafenzaTheForemostEffect extends ReplacementEffectImpl {

    public AnafenzaTheForemostEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If a nontoken creature an opponent owns would die or a creature card not on the battlefield would be put into an opponent's graveyard, exile that card instead";
    }

    public AnafenzaTheForemostEffect(final AnafenzaTheForemostEffect effect) {
        super(effect);
    }

    @Override
    public AnafenzaTheForemostEffect copy() {
        return new AnafenzaTheForemostEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD) {
                Permanent permanent = ((ZoneChangeEvent) event).getTarget();
                if (permanent != null && !(permanent instanceof PermanentToken)) {
                    return controller.moveCards(permanent, Zone.EXILED, source, game);
                }
            } else {
                Card card = game.getCard(event.getTargetId());
                if (card != null) {
                    return controller.moveCards(card, Zone.EXILED, source, game);
                }
            }
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getToZone() == Zone.GRAVEYARD) {
            Card card = game.getCard(event.getTargetId());
            if (card != null && game.getOpponents(source.getControllerId()).contains(card.getOwnerId())) { // Anafenza only cares about cards
                if (zEvent.getTarget() != null) { // if it comes from permanent, check if it was a creature on the battlefield
                    if (zEvent.getTarget().isCreature(game)) {
                        return true;
                    }
                } else if (card.isCreature(game)) {
                    return true;
                }
            }
        }
        return false;
    }

}
