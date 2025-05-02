package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.common.OpponentsLostLifeCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MenaceAbility;
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
 * @author Styxo
 */
public final class SithLord extends CardImpl {

    private static final String rule = "with X +1/+1 counters on it, where X is the total life lost by your opponents this turn";

    public SithLord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SITH);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility());

        // Sith Lord enters the battlefield with X +1/+1 counters on it, where X is the total life lost by your opponents this turn.
        this.addAbility(new EntersBattlefieldAbility(new SithLordEffect(), rule));
    }

    private SithLord(final SithLord card) {
        super(card);
    }

    @Override
    public SithLord copy() {
        return new SithLord(this);
    }

    static class SithLordEffect extends OneShotEffect {

        SithLordEffect() {
            super(Outcome.BoostCreature);
        }

        private SithLordEffect(final SithLordEffect effect) {
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
        public SithLordEffect copy() {
            return new SithLordEffect(this);
        }
    }

}
