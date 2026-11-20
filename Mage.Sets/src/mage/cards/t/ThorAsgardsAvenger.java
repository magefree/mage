package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author DarioBarrabinoDev
 */
public final class ThorAsgardsAvenger extends CardImpl {

    public ThorAsgardsAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // If another source you control would deal damage to an opponent or a permanent an opponent controls, it deals that much damage plus 1 instead.
        this.addAbility(new SimpleStaticAbility(new ThorAsgardsAvengerEffect()));
    }

    private ThorAsgardsAvenger(final ThorAsgardsAvenger card) {
        super(card);
    }

    @Override
    public ThorAsgardsAvenger copy() {
        return new ThorAsgardsAvenger(this);
    }
}

class ThorAsgardsAvengerEffect extends ReplacementEffectImpl {

    ThorAsgardsAvengerEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        this.staticText = "if another source you control would deal damage to an opponent "
                + "or a permanent an opponent controls, it deals that much damage plus 1 instead";
    }

    private ThorAsgardsAvengerEffect(final ThorAsgardsAvengerEffect effect) {
        super(effect);
    }

    @Override
    public ThorAsgardsAvengerEffect copy() {
        return new ThorAsgardsAvengerEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowInc(event.getAmount(), 1));
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGE_PERMANENT:
            case DAMAGE_PLAYER:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        return controller != null
                && event.getAmount() > 0
                && !event.getSourceId().equals(source.getSourceId())
                && source.isControlledBy(game.getControllerId(event.getSourceId()))
                && controller.hasOpponent(getControllerOrSelf(event.getTargetId(), game), game);
    }

    private static UUID getControllerOrSelf(UUID id, Game game) {
        UUID outId = game.getControllerId(id);
        return outId == null ? id : outId;
    }
}
