package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CradleOfVitality extends CardImpl {

    public CradleOfVitality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // Whenever you gain life, you may pay {1}{W}. If you do, put a +1/+1 counter on target creature for each 1 life you gained.
        Ability ability = new GainLifeControllerTriggeredAbility(new DoIfCostPaid(
                new CradleOfVitalityEffect(), new ManaCostsImpl<>("{1}{W}")
        ), false, true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private CradleOfVitality(final CradleOfVitality card) {
        super(card);
    }

    @Override
    public CradleOfVitality copy() {
        return new CradleOfVitality(this);
    }
}

class CradleOfVitalityEffect extends OneShotEffect {

    CradleOfVitalityEffect() {
        super(Outcome.Benefit);
        staticText = "put a +1/+1 counter on target creature for each 1 life you gained";
    }

    private CradleOfVitalityEffect(final CradleOfVitalityEffect effect) {
        super(effect);
    }

    @Override
    public CradleOfVitalityEffect copy() {
        return new CradleOfVitalityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        int lifeGained = 0;
        if (this.getValue("gainedLife") != null) {
            lifeGained = (Integer) this.getValue("gainedLife");
        }
        return permanent != null && lifeGained > 0
                && permanent.addCounters(CounterType.P1P1.createInstance(lifeGained), source.getControllerId(), source, game);
    }
}
