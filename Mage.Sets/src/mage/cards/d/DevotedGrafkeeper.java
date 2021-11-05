package mage.cards.d;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.DisturbAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DevotedGrafkeeper extends CardImpl {

    public DevotedGrafkeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PEASANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.secondSideCardClazz = mage.cards.d.DepartedSoulkeeper.class;

        // When Devoted Grafkeeper enters the battlefield, mill two cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(2)));

        // Whenever you cast a spell from your graveyard, tap target creature you don't control.
        this.addAbility(new DevotedGrafkeeperTriggeredAbility());

        // Disturb {1}{W}{U}
        this.addAbility(new TransformAbility());
        this.addAbility(new DisturbAbility(new ManaCostsImpl<>("{1}{W}{U}")));
    }

    private DevotedGrafkeeper(final DevotedGrafkeeper card) {
        super(card);
    }

    @Override
    public DevotedGrafkeeper copy() {
        return new DevotedGrafkeeper(this);
    }
}

class DevotedGrafkeeperTriggeredAbility extends TriggeredAbilityImpl {

    DevotedGrafkeeperTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TapTargetEffect(), false);
        this.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
    }

    private DevotedGrafkeeperTriggeredAbility(DevotedGrafkeeperTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return isControlledBy(event.getPlayerId()) && event.getZone() == Zone.GRAVEYARD;
    }

    @Override
    public DevotedGrafkeeperTriggeredAbility copy() {
        return new DevotedGrafkeeperTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you cast a spell from your graveyard, tap target creature you don't control.";
    }
}
