package mage.cards.h;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.EnchantedPredicate;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HatefulEidolon extends CardImpl {

    public HatefulEidolon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever an enchanted creature dies, draw a card for each Aura you controlled that was attached to it.
        this.addAbility(new HatefulEidolonTriggeredAbility());
    }

    private HatefulEidolon(final HatefulEidolon card) {
        super(card);
    }

    @Override
    public HatefulEidolon copy() {
        return new HatefulEidolon(this);
    }
}

class HatefulEidolonTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(EnchantedPredicate.instance);
    }

    HatefulEidolonTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
    }

    private HatefulEidolonTriggeredAbility(final HatefulEidolonTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HatefulEidolonTriggeredAbility copy() {
        return new HatefulEidolonTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (!zEvent.isDiesEvent() || !filter.match(zEvent.getTarget(), game)) {
            return false;
        }
        int auraCount = zEvent
                .getTarget()
                .getAttachments()
                .stream()
                .map(game::getPermanentOrLKIBattlefield)
                .filter(Objects::nonNull)
                .filter(permanent -> permanent.hasSubtype(SubType.AURA, game))
                .map(Controllable::getControllerId)
                .filter(this.getControllerId()::equals)
                .mapToInt(x -> 1)
                .sum();
        this.getEffects().clear();
        this.addEffect(new DrawCardSourceControllerEffect(auraCount));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever an enchanted creature dies, draw a card for each Aura you controlled that was attached to it.";
    }
}
