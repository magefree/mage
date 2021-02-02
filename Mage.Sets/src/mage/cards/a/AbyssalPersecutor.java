
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author Loki
 */
public final class AbyssalPersecutor extends CardImpl {

    public AbyssalPersecutor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying, trample
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(TrampleAbility.getInstance());
        
        // You can't win the game and your opponents can't lose the game.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AbyssalPersecutorCannotWinEffect()));
    }

    private AbyssalPersecutor(final AbyssalPersecutor card) {
        super(card);
    }

    @Override
    public AbyssalPersecutor copy() {
        return new AbyssalPersecutor(this);
    }
}

class AbyssalPersecutorCannotWinEffect extends ContinuousRuleModifyingEffectImpl {

    AbyssalPersecutorCannotWinEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, false, false);
        staticText = "You can't win the game and your opponents can't lose the game";
    }

    AbyssalPersecutorCannotWinEffect ( final AbyssalPersecutorCannotWinEffect effect ) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOSES || event.getType() == GameEvent.EventType.WINS ;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if ((event.getType() == GameEvent.EventType.LOSES && game.getOpponents(source.getControllerId()).contains(event.getPlayerId()))
                || (event.getType() == GameEvent.EventType.WINS && event.getPlayerId().equals(source.getControllerId()))) {
            return true;
        }
        return false;
    }

    @Override
    public AbyssalPersecutorCannotWinEffect copy() {
        return new AbyssalPersecutorCannotWinEffect(this);
    }
}
