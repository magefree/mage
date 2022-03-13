
package mage.cards.b;

import java.util.UUID;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SpellAbilityType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.BirdToken;

public final class BeckCall extends SplitCard {

    public BeckCall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}{U}", "{4}{W}{U}", SpellAbilityType.SPLIT_FUSED);

        // Beck
        // Whenever a creature enters the battlefield this turn, you may draw a card.
        getLeftHalfCard().getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new BeckTriggeredAbility()));

        // Call
        // Create four 1/1 white Bird creature tokens with flying.
        getRightHalfCard().getSpellAbility().addEffect(new CreateTokenEffect(new BirdToken(), 4));

    }

    private BeckCall(final BeckCall card) {
        super(card);
    }

    @Override
    public BeckCall copy() {
        return new BeckCall(this);
    }
}

class BeckTriggeredAbility extends DelayedTriggeredAbility {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    public BeckTriggeredAbility() {
        super(new DrawCardSourceControllerEffect(1), Duration.EndOfTurn, false);
        optional = true;
    }

    public BeckTriggeredAbility(BeckTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        UUID targetId = event.getTargetId();
        Permanent permanent = game.getPermanent(targetId);
        return filter.match(permanent, getControllerId(), this, game);
    }

    @Override
    public BeckTriggeredAbility copy() {
        return new BeckTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a creature enters the battlefield this turn, you may draw a card.";
    }
}
