package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public final class KreshTheBloodbraided extends CardImpl {

    public KreshTheBloodbraided(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever another creature dies, you may put X +1/+1 counters on Kresh the Bloodbraided, where X is that creature's power.
        this.addAbility(new DiesCreatureTriggeredAbility(new KreshTheBloodbraidedEffect(), true, true, true));
    }

    private KreshTheBloodbraided(final KreshTheBloodbraided card) {
        super(card);
    }

    @Override
    public KreshTheBloodbraided copy() {
        return new KreshTheBloodbraided(this);
    }
}

class KreshTheBloodbraidedEffect extends OneShotEffect {

    KreshTheBloodbraidedEffect() {
        super(Outcome.BoostCreature);
        staticText = "you may put X +1/+1 counters on {this}, where X is that creature's power";
    }

    KreshTheBloodbraidedEffect(final KreshTheBloodbraidedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        Permanent kreshTheBloodbraided = game.getPermanent(source.getSourceId());
        if (permanent != null && kreshTheBloodbraided != null) {
            int amount = permanent.getPower().getValue();
            if (amount > 0) {
                kreshTheBloodbraided.addCounters(CounterType.P1P1.createInstance(amount), source.getControllerId(), source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public KreshTheBloodbraidedEffect copy() {
        return new KreshTheBloodbraidedEffect(this);
    }
}
