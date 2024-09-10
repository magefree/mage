package mage.cards.s;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShardOfTheVoidDragon extends CardImpl {

    public ShardOfTheVoidDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}{B}");

        this.subtype.add(SubType.CTAN);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Sphere of the Void Dragon -- Whenever Shard of the Void Dragon attacks, each opponent sacrifices a nonland permanent.
        this.addAbility(new AttacksTriggeredAbility(
                new SacrificeOpponentsEffect(StaticFilters.FILTER_PERMANENT_NON_LAND)
        ).withFlavorWord("Spear of the Void Dragon"));

        // Matter Absorption -- Whenever an artifact is put into a graveyard from the battlefield or is put into exile from the battlefield, put two +1/+1 counters on Shard of the Void Dragon.
        this.addAbility(new ShardOfTheVoidDragonTriggeredAbility());
    }

    private ShardOfTheVoidDragon(final ShardOfTheVoidDragon card) {
        super(card);
    }

    @Override
    public ShardOfTheVoidDragon copy() {
        return new ShardOfTheVoidDragon(this);
    }
}

class ShardOfTheVoidDragonTriggeredAbility extends TriggeredAbilityImpl {

    ShardOfTheVoidDragonTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)));
        setTriggerPhrase("Whenever an artifact is put into a graveyard from the battlefield or is put into exile from the battlefield, ");
        withFlavorWord("Matter Absorption");
    }

    private ShardOfTheVoidDragonTriggeredAbility(final ShardOfTheVoidDragonTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ShardOfTheVoidDragonTriggeredAbility copy() {
        return new ShardOfTheVoidDragonTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent.getTarget() != null
                && zEvent.getTarget().isArtifact(game)
                && zEvent.getFromZone() == Zone.BATTLEFIELD
                && (zEvent.getToZone() == Zone.GRAVEYARD || zEvent.getToZone() == Zone.EXILED);
    }
}
