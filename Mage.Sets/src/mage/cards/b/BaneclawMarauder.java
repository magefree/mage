package mage.cards.b;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BecomesBlockedAllTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifePermanentControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.NightboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.BlockingAttackerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;

import java.util.UUID;

public final class BaneclawMarauder extends CardImpl {

    public static final String CARDNAME = "Baneclaw Marauder";

    public BaneclawMarauder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);
        this.color.setBlack(true);
        this.transformable = true;
        this.nightCard = true;

        // Whenever Baneclaw Marauder becomes blocked, each creature blocking it gets -1/-1 until end of turn.
        this.addAbility(new BecomesBlockedAllTriggeredAbility(new BoostTargetEffect(-1, -1, Duration.EndOfTurn), false));

        // Whenever a creature blocking Baneclaw Marauder dies, that creature's controller loses 1 life.
        this.addAbility(new BaneclawMarauderTriggeredAbility(this.getId(), new LoseLifePermanentControllerEffect(1)));

        // Nightbound
        this.addAbility(new NightboundAbility());
    }

    private BaneclawMarauder(final BaneclawMarauder card) {
        super(card);
    }

    @Override
    public BaneclawMarauder copy() {
        return new BaneclawMarauder(this);
    }
}

class BaneclawMarauderTriggeredAbility extends TriggeredAbilityImpl {

    protected UUID attackerId;

    public BaneclawMarauderTriggeredAbility(UUID id, Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
        this.attackerId = id;
    }

    public BaneclawMarauderTriggeredAbility(final mage.cards.b.BaneclawMarauderTriggeredAbility ability) {
        super(ability);
        this.attackerId = ability.attackerId;
    }

    @Override
    public mage.cards.b.BaneclawMarauderTriggeredAbility copy() {
        return new mage.cards.b.BaneclawMarauderTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;

        return creatureBlockingThisDied(zEvent, game);
    }

    private boolean creatureBlockingThisDied(ZoneChangeEvent event, Game game) {
        return isDeathEvent(event) && targetWasBlockingThis(event, game);
    }

    private boolean isDeathEvent(ZoneChangeEvent event) {
        return event.isDiesEvent() && event.getTarget() != null;
    }

    private boolean targetWasBlockingThis(ZoneChangeEvent event, Game game) {
        FilterCreaturePermanent filter = new FilterCreaturePermanent("creature blocking {this}");
        filter.add(new BlockingAttackerIdPredicate(this.id));

        return filter.match(event.getTarget(), getSourceId(), getControllerId(), game);
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever a creature blocking {this} dies, ";
    }
}