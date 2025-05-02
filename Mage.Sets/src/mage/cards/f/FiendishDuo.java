package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FiendishDuo extends CardImpl {

    public FiendishDuo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.subtype.add(SubType.DEVIL);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // If a source would deal damage to an opponent, it deals double that damage to that player instead.
        this.addAbility(new SimpleStaticAbility(new FiendishDuoEffect()));
    }

    private FiendishDuo(final FiendishDuo card) {
        super(card);
    }

    @Override
    public FiendishDuo copy() {
        return new FiendishDuo(this);
    }
}

class FiendishDuoEffect extends ReplacementEffectImpl {

    FiendishDuoEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        staticText = "If a source would deal damage to an opponent, " +
                "it deals double that damage to that player instead";
    }

    private FiendishDuoEffect(final FiendishDuoEffect effect) {
        super(effect);
    }

    @Override
    public FiendishDuoEffect copy() {
        return new FiendishDuoEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(source.getControllerId());
        return player != null && player.hasOpponent(event.getTargetId(), game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowMultiply(event.getAmount(), 2));
        return false;
    }
}
