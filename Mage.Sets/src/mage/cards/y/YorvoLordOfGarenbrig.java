package mage.cards.y;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YorvoLordOfGarenbrig extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public YorvoLordOfGarenbrig(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Yorvo, Lord of Garenbrig enters the battlefield with four +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(4)),
                "with four +1/+1 counters on it"
        ));

        // Whenever another green creature enters the battlefield under your control, put a +1/+1 counter on Yorvo. Then if that creature's power is greater than Yorvo's power, put another +1/+1 counter on Yorvo.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD, new YorvoLordOfGarenbrigEffect(), filter, false, SetTargetPointer.PERMANENT,
                "Whenever another green creature enters the battlefield under your control, " +
                        "put a +1/+1 counter on {this}. Then if that creature's power is greater than {this}'s power, " +
                        "put another +1/+1 counter on {this}."
        ));
    }

    private YorvoLordOfGarenbrig(final YorvoLordOfGarenbrig card) {
        super(card);
    }

    @Override
    public YorvoLordOfGarenbrig copy() {
        return new YorvoLordOfGarenbrig(this);
    }
}

class YorvoLordOfGarenbrigEffect extends OneShotEffect {

    YorvoLordOfGarenbrigEffect() {
        super(Outcome.Benefit);
    }

    private YorvoLordOfGarenbrigEffect(final YorvoLordOfGarenbrigEffect effect) {
        super(effect);
    }

    @Override
    public YorvoLordOfGarenbrigEffect copy() {
        return new YorvoLordOfGarenbrigEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePerm = game.getPermanent(source.getSourceId());
        if (sourcePerm == null) {
            return false;
        }
        sourcePerm.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (permanent == null) {
            return true;
        }
        game.getState().processAction(game);
        if (permanent.getPower().getValue() > sourcePerm.getPower().getValue()) {
            sourcePerm.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
        }
        return true;
    }
}
