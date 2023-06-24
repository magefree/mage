package mage.cards.m;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SpiritWhiteToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MillicentRestlessRevenant extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.SPIRIT);
    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Spirits you control", xValue);

    public MillicentRestlessRevenant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // This spell costs {1} less to cast for each Spirit you control.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionForEachSourceEffect(1, xValue)
        ).addHint(hint));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Millicent, Restless Revenant or another nontoken Spirit you control dies or deals combat damage to a player, create a 1/1 white Spirit creature token with flying.
        this.addAbility(new MillicentRestlessRevenantTriggeredAbility());
    }

    private MillicentRestlessRevenant(final MillicentRestlessRevenant card) {
        super(card);
    }

    @Override
    public MillicentRestlessRevenant copy() {
        return new MillicentRestlessRevenant(this);
    }
}

class MillicentRestlessRevenantTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterPermanent filterNontoken = new FilterControlledPermanent(SubType.SPIRIT);

    static {
        filterNontoken.add(TokenPredicate.FALSE);
    }

    MillicentRestlessRevenantTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new SpiritWhiteToken()));
    }

    private MillicentRestlessRevenantTriggeredAbility(final MillicentRestlessRevenantTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MillicentRestlessRevenantTriggeredAbility copy() {
        return new MillicentRestlessRevenantTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE
                || event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
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
            case DAMAGED_PLAYER:
                if (!((DamagedEvent) event).isCombatDamage()) {
                    return false;
                }
                permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
                break;
            default:
                return false;
        }
        if (permanent == null) {
            return false;
        }
        return permanent.getId().equals(this.getSourceId())
                || filterNontoken.match(permanent, getControllerId(), this, game);
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject source, GameEvent event) {
        return TriggeredAbilityImpl.isInUseableZoneDiesTrigger(this, event, game);
    }

    @Override
    public String getRule() {
        return "Whenever {this} or another nontoken Spirit you control dies or deals combat damage to a player, " +
                "create a 1/1 white Spirit creature token with flying.";
    }
}
