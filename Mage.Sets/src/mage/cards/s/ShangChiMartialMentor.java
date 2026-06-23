package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.PowerUpAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class ShangChiMartialMentor extends CardImpl {

    public ShangChiMartialMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // If one or more +1/+1 counters would be put on a creature you control, twice that many +1/+1 counters are put on that creature instead.
        this.addAbility(new SimpleStaticAbility(new ShangChiMartialMentorReplacementEffect()));

        // Power-up -- {5}{G}{G}: Put three +1/+1 counters on Shang-Chi.
        this.addAbility(new PowerUpAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance(3)),
            new ManaCostsImpl<>("{5}{G}{G}")
        ));
    }

    private ShangChiMartialMentor(final ShangChiMartialMentor card) {
        super(card);
    }

    @Override
    public ShangChiMartialMentor copy() {
        return new ShangChiMartialMentor(this);
    }
}

class ShangChiMartialMentorReplacementEffect extends ReplacementEffectImpl {

    ShangChiMartialMentorReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature, false);
        staticText = "If one or more +1/+1 counters would be put on a creature you control, twice that many +1/+1 counters are put on it instead";
    }

    private ShangChiMartialMentorReplacementEffect(final ShangChiMartialMentorReplacementEffect effect) {
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
    public ShangChiMartialMentorReplacementEffect copy() {
        return new ShangChiMartialMentorReplacementEffect(this);
    }
}
