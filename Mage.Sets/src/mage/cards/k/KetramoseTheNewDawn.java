package mage.cards.k;

import mage.MageInt;
import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInExileCondition;
import mage.abilities.dynamicvalue.common.CardsInExileCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.combat.CantAttackBlockUnlessConditionSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeBatchEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.events.ZoneChangeGroupEvent;

import java.util.Objects;
import java.util.UUID;

/**
 * @author Jmlundeen
 */
public final class KetramoseTheNewDawn extends CardImpl {

    public KetramoseTheNewDawn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // Ketramose can't attack or block unless there are seven or more cards in exile.
        this.addAbility(new SimpleStaticAbility(
            new CantAttackBlockUnlessConditionSourceEffect(new CardsInExileCondition(ComparisonType.OR_GREATER, 7))
        ).addHint(CardsInExileCount.ALL.getHint()));

        // Whenever one or more cards are put into exile from graveyards and/or the battlefield during your turn, you draw a card and lose 1 life.
        this.addAbility(new KetramoseTriggeredAbility());

    }

    private KetramoseTheNewDawn(final KetramoseTheNewDawn card) {
        super(card);
    }

    @Override
    public KetramoseTheNewDawn copy() {
        return new KetramoseTheNewDawn(this);
    }

}
class KetramoseTriggeredAbility extends TriggeredAbilityImpl {
    KetramoseTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), false);
        this.addEffect(new LoseLifeSourceControllerEffect(1));
    }

    private KetramoseTriggeredAbility(final KetramoseTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_GROUP;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return game.getActivePlayerId().equals(getControllerId())
        && zEvent.getFromZone() == Zone.GRAVEYARD || zEvent.getFromZone() == Zone.BATTLEFIELD;
    }

    @Override
    public TriggeredAbility copy()
    {
        return new KetramoseTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever one or more cards are put into exile from graveyards"
        + " and/or the battlefield during your turn, you draw a card and lose 1 life.";
    }
}
