

package mage.cards.f;

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
import mage.game.stack.StackObject;
import mage.util.CardUtil;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class FireServant extends CardImpl {

    public FireServant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // If a red instant or sorcery spell you control would deal damage, it deals double that damage instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new FireServantEffect()));
    }

    private FireServant(final FireServant card) {
        super(card);
    }

    @Override
    public FireServant copy() {
        return new FireServant(this);
    }

}

class FireServantEffect extends ReplacementEffectImpl {

    public FireServantEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        staticText = "If a red instant or sorcery spell you control would deal damage, it deals double that damage instead";
    }

    private FireServantEffect(final FireServantEffect effect) {
        super(effect);
    }

    @Override
    public FireServantEffect copy() {
        return new FireServantEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PERMANENT ||
                event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        StackObject spell = game.getStack().getStackObject(event.getSourceId());
        return spell != null &&
                spell.isControlledBy(source.getControllerId()) &&
                spell.getColor(game).isRed() &&
                spell.isInstantOrSorcery(game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowMultiply(event.getAmount(), 2));
        return false;
    }

}
