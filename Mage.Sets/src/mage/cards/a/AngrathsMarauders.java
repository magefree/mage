
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.util.CardUtil;

/**
 *
 * @author TheElk801
 */
public final class AngrathsMarauders extends CardImpl {

    public AngrathsMarauders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // If a source you control would deal damage to a permanent or player, it deals double that damage to that permanent or player instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AngrathsMaraudersEffect()));
    }

    public AngrathsMarauders(final AngrathsMarauders card) {
        super(card);
    }

    @Override
    public AngrathsMarauders copy() {
        return new AngrathsMarauders(this);
    }
}

class AngrathsMaraudersEffect extends ReplacementEffectImpl {

    public AngrathsMaraudersEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        staticText = "If a source you control would deal damage to a permanent or player, it deals double that damage to that permanent or player instead";
    }

    public AngrathsMaraudersEffect(final AngrathsMaraudersEffect effect) {
        super(effect);
    }

    @Override
    public AngrathsMaraudersEffect copy() {
        return new AngrathsMaraudersEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGE_PLAYER:
                return true;
            case DAMAGE_CREATURE:
                return true;
            case DAMAGE_PLANESWALKER:
                return true;
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return game.getControllerId(event.getSourceId()).equals(source.getControllerId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.addWithOverflowCheck(event.getAmount(), event.getAmount()));
        return false;
    }
}
