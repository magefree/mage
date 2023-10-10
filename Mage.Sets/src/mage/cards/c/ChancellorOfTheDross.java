
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.ChancellorAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author BetaSteward
 */
public final class ChancellorOfTheDross extends CardImpl {

    private static String abilityText = "at the beginning of the first upkeep, each opponent loses 3 life, then you gain life equal to the life lost this way";

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
        super(new ChancellorOfTheDrossEffect());
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

class ChancellorOfTheDrossEffect extends OneShotEffect {

    ChancellorOfTheDrossEffect () {
        super(Outcome.Benefit);
        staticText = "each opponent loses 3 life, then you gain life equal to the life lost this way";
    }

    private ChancellorOfTheDrossEffect(final ChancellorOfTheDrossEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int loseLife = 0;
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            loseLife += game.getPlayer(opponentId).loseLife(3, game, source, false);
        }
        if (loseLife > 0) {
            game.getPlayer(source.getControllerId()).gainLife(loseLife, game, source);
        }
        return true;
    }

    @Override
    public ChancellorOfTheDrossEffect copy() {
        return new ChancellorOfTheDrossEffect(this);
    }

}
