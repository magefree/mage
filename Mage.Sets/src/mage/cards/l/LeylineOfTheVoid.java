

package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.LeylineAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class LeylineOfTheVoid extends CardImpl {

    public LeylineOfTheVoid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");

        // If Leyline of the Void is in your opening hand, you may begin the game with it on the battlefield.
        this.addAbility(LeylineAbility.getInstance());
        // If a card would be put into an opponent's graveyard from anywhere, exile it instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LeylineOfTheVoidEffect()));
    }

    private LeylineOfTheVoid(final LeylineOfTheVoid card) {
        super(card);
    }

    @Override
    public LeylineOfTheVoid copy() {
        return new LeylineOfTheVoid(this);
    }

}

class LeylineOfTheVoidEffect extends ReplacementEffectImpl {

    public LeylineOfTheVoidEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If a card would be put into an opponent's graveyard from anywhere, exile it instead";
    }

    private LeylineOfTheVoidEffect(final LeylineOfTheVoidEffect effect) {
        super(effect);
    }

    @Override
    public LeylineOfTheVoidEffect copy() {
        return new LeylineOfTheVoidEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ((ZoneChangeEvent) event).setToZone(Zone.EXILED);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD) {
            Card card = game.getCard(event.getTargetId());
            if (card != null && game.getOpponents(source.getControllerId()).contains(card.getOwnerId())) {
                return true;
            }
        }
        return false;
    }

}
