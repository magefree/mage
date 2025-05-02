package mage.cards.c;

import mage.MageInt;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.ChancellorAbility;
import mage.abilities.effects.common.LoseLifeOpponentsYouGainLifeLostEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 *
 * @author BetaSteward
 */
public final class ChancellorOfTheDross extends CardImpl {

    private static final String abilityText = "at the beginning of the first upkeep, each opponent loses 3 life, then you gain life equal to the life lost this way";

    public ChancellorOfTheDross(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{B}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.VAMPIRE);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // You may reveal this card from your opening hand. If you do, at the beginning of the first upkeep, each opponent loses 3 life, then you gain life equal to the life lost this way.
        this.addAbility(new ChancellorAbility(new ChancellorOfTheDrossDelayedTriggeredAbility(), abilityText));

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(LifelinkAbility.getInstance());
    }

    private ChancellorOfTheDross(final ChancellorOfTheDross card) {
        super(card);
    }

    @Override
    public ChancellorOfTheDross copy() {
        return new ChancellorOfTheDross(this);
    }
}

class ChancellorOfTheDrossDelayedTriggeredAbility extends DelayedTriggeredAbility {

    ChancellorOfTheDrossDelayedTriggeredAbility () {
        super(new LoseLifeOpponentsYouGainLifeLostEffect(3));
    }

    private ChancellorOfTheDrossDelayedTriggeredAbility(final ChancellorOfTheDrossDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return true;
    }
    @Override
    public ChancellorOfTheDrossDelayedTriggeredAbility copy() {
        return new ChancellorOfTheDrossDelayedTriggeredAbility(this);
    }
}
