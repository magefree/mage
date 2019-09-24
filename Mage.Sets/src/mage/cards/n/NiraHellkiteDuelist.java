package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author jmharmon
 */

public final class NiraHellkiteDuelist extends CardImpl {

    public NiraHellkiteDuelist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}{B}{R}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying, trample, haste
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());

        // When Nira, Hellkite Duelist enters the battlefield, the next time you would lose the game this turn, instead draw three cards and your life total becomes 5.
        this.addAbility(new EntersBattlefieldAbility(new NiraHellkiteDuelistEffect(), false));
    }

    public NiraHellkiteDuelist(final NiraHellkiteDuelist card) {
        super(card);
    }

    @Override
    public NiraHellkiteDuelist copy() {
        return new NiraHellkiteDuelist(this);
    }
}

class NiraHellkiteDuelistEffect extends ReplacementEffectImpl {

    public NiraHellkiteDuelistEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "the next time you would lose the game this turn, instead draw three cards and your life total becomes 5";
    }

    public NiraHellkiteDuelistEffect(final NiraHellkiteDuelistEffect effect) {
        super(effect);
    }

    @Override
    public NiraHellkiteDuelistEffect copy() {
        return new NiraHellkiteDuelistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if (player != null) {
            player.drawCards(3, game);
            player.setLife(5, game, source);
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOSES;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getPlayerId().equals(source.getControllerId())) {
            return true;
        }
        return false;
    }
}
