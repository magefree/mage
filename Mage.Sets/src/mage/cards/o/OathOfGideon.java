
package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.KorAllyToken;

/**
 *
 * @author LevelX2
 */
public final class OathOfGideon extends CardImpl {

    public OathOfGideon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}");
        this.supertype.add(SuperType.LEGENDARY);

        // When Oath of Gideon enters the battlefield, create two 1/1 Kor Ally creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new KorAllyToken(), 2), false));

        // Each planeswalker you control enters the battlefield with an additional loyalty counter on it.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new OathOfGideonReplacementEffect()));
    }

    private OathOfGideon(final OathOfGideon card) {
        super(card);
    }

    @Override
    public OathOfGideon copy() {
        return new OathOfGideon(this);
    }
}

class OathOfGideonReplacementEffect extends ReplacementEffectImpl {

    OathOfGideonReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Each planeswalker you control enters the battlefield with an additional loyalty counter on it";
    }

    OathOfGideonReplacementEffect(OathOfGideonReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        return creature != null && creature.isControlledBy(source.getControllerId())
                && creature.isPlaneswalker(game)
                && !event.getTargetId().equals(source.getSourceId());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        if (creature != null) {
            creature.addCounters(CounterType.LOYALTY.createInstance(), source.getControllerId(), source, game);
        }
        return false;
    }

    @Override
    public OathOfGideonReplacementEffect copy() {
        return new OathOfGideonReplacementEffect(this);
    }
}
