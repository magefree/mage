
package mage.cards.b;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;

import java.util.UUID;

/**
 *
 * @author TheElk801
 */
public final class BoneyardScourge extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a Dragon you control");

    static {
        filter.add(SubType.DRAGON.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public BoneyardScourge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.subtype.add(SubType.ZOMBIE, SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a Dragon you control dies while Boneyard Scourge is in your graveyard, you may pay 1B. If you do, return Boneyard Scourge from your graveyard to the battlefield.
        TriggeredAbility ability = new DiesWhileInGraveyardTriggeredAbility(
                new DoIfCostPaid(new ReturnSourceFromGraveyardToBattlefieldEffect(), new ManaCostsImpl("{1}{B}")),
                filter);
        this.addAbility(ability);
    }

    private BoneyardScourge(final BoneyardScourge card) {
        super(card);
    }

    @Override
    public BoneyardScourge copy() {
        return new BoneyardScourge(this);
    }
}

class DiesWhileInGraveyardTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterCreaturePermanent filter;

    public DiesWhileInGraveyardTriggeredAbility(Effect effect, FilterCreaturePermanent filter) {
        super(Zone.GRAVEYARD, effect, false);
        this.filter = filter;
    }

    public DiesWhileInGraveyardTriggeredAbility(final DiesWhileInGraveyardTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
    }

    @Override
    public DiesWhileInGraveyardTriggeredAbility copy() {
        return new DiesWhileInGraveyardTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (!zEvent.isDiesEvent()) { return false; }

        for (Zone z : Zone.values()) {
            if (game.getShortLivingLKI(sourceId, z) && z != Zone.GRAVEYARD) {
                return false;
            }
        }

        return filter.match(zEvent.getTarget(), controllerId,this, game);
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever " + filter.getMessage() + " dies while {this} is in your graveyard, " ;
    }
}
