package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.common.OpponentsLostLifeCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class CryptbornHorror extends CardImpl {

    private static final String rule = "with X +1/+1 counters on it, where X is the total life lost by your opponents this turn";

    public CryptbornHorror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B/R}{B/R}");
        this.subtype.add(SubType.HORROR);
        this.color.setBlack(true);
        this.color.setRed(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Cryptborn Horror enters the battlefield with X +1/+1 counters on it, where X is the total life lost by your opponents this turn.
        this.addAbility(new EntersBattlefieldAbility(new CryptbornHorrorEffect(), rule));
    }

    private CryptbornHorror(final CryptbornHorror card) {
        super(card);
    }

    @Override
    public CryptbornHorror copy() {
        return new CryptbornHorror(this);
    }
}

class CryptbornHorrorEffect extends OneShotEffect {

    CryptbornHorrorEffect() {
        super(Outcome.BoostCreature);
    }

    private CryptbornHorrorEffect(final CryptbornHorrorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (permanent != null) {
            int oll = OpponentsLostLifeCount.instance.calculate(game, source, this);
            if (oll > 0) {
                permanent.addCounters(CounterType.P1P1.createInstance(oll), source.getControllerId(), source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public CryptbornHorrorEffect copy() {
        return new CryptbornHorrorEffect(this);
    }
}
