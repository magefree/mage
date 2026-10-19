package mage.cards.l;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.SeekCardEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.filter.predicate.mageobject.PermanentPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

public final class LaezelBlessedWarrior extends CardImpl {

    private static final FilterCard filter = new FilterCard("nonland permanent card with mana value 3 or less");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
        filter.add(PermanentPredicate.instance);
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public LaezelBlessedWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GITH);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        this.extraDeckCard = true;
        this.addAbility(DoubleStrikeAbility.getInstance());
        this.addAbility(new LaezelSpecializedTriggeredAbility(new SeekCardEffect(filter)));
    }

    private LaezelBlessedWarrior(final LaezelBlessedWarrior card) {
        super(card);
    }

    @Override
    public LaezelBlessedWarrior copy() {
        return new LaezelBlessedWarrior(this);
    }
}

class LaezelSpecializedTriggeredAbility extends TriggeredAbilityImpl {

    LaezelSpecializedTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
        setTriggerPhrase("When {this} enters or specializes, ");
    }

    private LaezelSpecializedTriggeredAbility(final LaezelSpecializedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LaezelSpecializedTriggeredAbility copy() {
        return new LaezelSpecializedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD || event.getType() == GameEvent.EventType.SPECIALIZED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(getSourceId());
    }
}
