
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.SubType;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class CraftyCutpurse extends CardImpl {

    public CraftyCutpurse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Crafty Cutpurse enters the battlefield, each token that would be created under an opponent's control this turn is created under your control instead.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CraftyCutpurseReplacementEffect(), false));

    }

    private CraftyCutpurse(final CraftyCutpurse card) {
        super(card);
    }

    @Override
    public CraftyCutpurse copy() {
        return new CraftyCutpurse(this);
    }
}

class CraftyCutpurseReplacementEffect extends ReplacementEffectImpl {

    public CraftyCutpurseReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.GainControl);
        staticText = "each token that would be created under an opponent's control this turn is created under your control instead";
    }

    private CraftyCutpurseReplacementEffect(final CraftyCutpurseReplacementEffect effect) {
        super(effect);
    }

    @Override
    public CraftyCutpurseReplacementEffect copy() {
        return new CraftyCutpurseReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE || event.getType() == GameEvent.EventType.CREATE_TOKEN;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.CREATE_TOKEN) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null && controller.hasOpponent(event.getPlayerId(), game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            event.setPlayerId(controller.getId());
        }
        return false;
    }
}
