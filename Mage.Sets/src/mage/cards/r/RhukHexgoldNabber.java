package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.EquippedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RhukHexgoldNabber extends CardImpl {

    public RhukHexgoldNabber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.REBEL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever an equipped creature you control other than Rhuk, Hexgold Nabber attacks or dies, you may attach all Equipment attached to that creature to Rhuk.
        this.addAbility(new RhukHexgoldNabberTriggeredAbility());
    }

    private RhukHexgoldNabber(final RhukHexgoldNabber card) {
        super(card);
    }

    @Override
    public RhukHexgoldNabber copy() {
        return new RhukHexgoldNabber(this);
    }
}

class RhukHexgoldNabberTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(EquippedPredicate.instance);
    }

    RhukHexgoldNabberTriggeredAbility() {
        super(Zone.BATTLEFIELD, new RhukHexgoldNabberEffect(), true);
    }

    private RhukHexgoldNabberTriggeredAbility(final RhukHexgoldNabberTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RhukHexgoldNabberTriggeredAbility copy() {
        return new RhukHexgoldNabberTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE
                || event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent;
        switch (event.getType()) {
            case ZONE_CHANGE:
                ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
                if (!zEvent.isDiesEvent()) {
                    return false;
                }
                permanent = zEvent.getTarget();
                break;
            case ATTACKER_DECLARED:
                permanent = game.getPermanent(event.getSourceId());
            default:
                return false;
        }
        if (permanent == null || !filter.match(permanent, getControllerId(), this, game)) {
            return false;
        }
        this.getEffects().setValue("equippedPermanent", permanent);
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever an equipped creature you control other than {this} attacks or dies, " +
                "you may attach all Equipment attached to that creature to {this}.";
    }
}

class RhukHexgoldNabberEffect extends OneShotEffect {

    RhukHexgoldNabberEffect() {
        super(Outcome.Benefit);
    }

    private RhukHexgoldNabberEffect(final RhukHexgoldNabberEffect effect) {
        super(effect);
    }

    @Override
    public RhukHexgoldNabberEffect copy() {
        return new RhukHexgoldNabberEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        Permanent permanent = (Permanent) getValue("equippedPermanent");
        if (sourcePermanent == null || permanent == null) {
            return false;
        }
        for (UUID attachmentId : permanent.getAttachments()) {
            Permanent attachment = game.getPermanent(attachmentId);
            if (attachment != null && attachment.hasSubtype(SubType.EQUIPMENT, game)) {
                sourcePermanent.addAttachment(attachmentId, source, game);
            }
        }
        return true;
    }
}
