package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * http://www.wizards.com/magic/magazine/article.aspx?x=mtg/faq/rtr
 * <p>
 * If a creature you control would enter the battlefield with a number of +1/+1
 * counters on it, it enters with twice that many instead.
 * <p>
 * If you control two Corpsejack Menaces, the number of +1/+1 counters placed is
 * four times the original number. Three Corpsejack Menaces multiplies the
 * original number by eight, and so on.
 *
 * @author LevelX2
 */
public final class CorpsejackMenace extends CardImpl {

    public CorpsejackMenace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");
        this.subtype.add(SubType.FUNGUS);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // If one or more +1/+1 counters would be put on a creature you control, twice that many +1/+1 counters are put on it instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CorpsejackMenaceReplacementEffect()));

    }

    private CorpsejackMenace(final CorpsejackMenace card) {
        super(card);
    }

    @Override
    public CorpsejackMenace copy() {
        return new CorpsejackMenace(this);
    }
}

class CorpsejackMenaceReplacementEffect extends ReplacementEffectImpl {

    CorpsejackMenaceReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature, false);
        staticText = "If one or more +1/+1 counters would be put on a creature you control, twice that many +1/+1 counters are put on it instead";
    }

    CorpsejackMenaceReplacementEffect(final CorpsejackMenaceReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmountForCounters(CardUtil.overflowMultiply(event.getAmount(), 2), true);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ADD_COUNTERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getData().equals(CounterType.P1P1.getName()) && event.getAmount() > 0) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent == null) {
                permanent = game.getPermanentEntering(event.getTargetId());
            }
            return permanent != null && permanent.isControlledBy(source.getControllerId())
                    && permanent.isCreature(game);
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public CorpsejackMenaceReplacementEffect copy() {
        return new CorpsejackMenaceReplacementEffect(this);
    }
}
