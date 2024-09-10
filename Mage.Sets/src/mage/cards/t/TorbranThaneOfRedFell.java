package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public final class TorbranThaneOfRedFell extends CardImpl {

    public TorbranThaneOfRedFell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // If a red source you control would deal damage to an opponent or a
        // permanent an opponent controls, it deals that much damage plus 2 instead.
        this.addAbility(new SimpleStaticAbility(new TorbranThaneOfRedFellEffect()));
    }

    private TorbranThaneOfRedFell(final TorbranThaneOfRedFell card) {
        super(card);
    }

    @Override
    public TorbranThaneOfRedFell copy() {
        return new TorbranThaneOfRedFell(this);
    }
}

class TorbranThaneOfRedFellEffect extends ReplacementEffectImpl {

    TorbranThaneOfRedFellEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        this.staticText = "If a red source you control would deal damage to an opponent "
                + "or a permanent an opponent controls, it deals that much damage plus 2 instead.";
    }

    private TorbranThaneOfRedFellEffect(final TorbranThaneOfRedFellEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowInc(event.getAmount(), 2));
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
        if (controller == null
                || !controller.hasOpponent(getControllerOrSelf(event.getTargetId(), game), game)
                || !source.isControlledBy(game.getControllerId(event.getSourceId()))) {
            return false;
        }
        MageObject sourceObject;
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        if (sourcePermanent == null) {
            sourceObject = game.getObject(event.getSourceId());
        } else {
            sourceObject = sourcePermanent;
        }

        return sourceObject != null
                && sourceObject.getColor(game).isRed()
                && event.getAmount() > 0;
    }

    private static UUID getControllerOrSelf(UUID id, Game game) {
        UUID outId = game.getControllerId(id);
        return outId == null ? id : outId;
    }

    @Override
    public TorbranThaneOfRedFellEffect copy() {
        return new TorbranThaneOfRedFellEffect(this);
    }

}
