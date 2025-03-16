package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ExhaustAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Jmlundeen
 */
public final class SitaVarmaMaskedRacer extends CardImpl {

    public SitaVarmaMaskedRacer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Exhaust -- {X}{G}{G}{U}: Put X +1/+1 counters on Sita Varma. Then you may have the base power and toughness of each other creature you control become equal to Sita Varma's power until end of turn.
        Cost cost = new ManaCostsImpl<>("{X}{G}{G}{U}");
        Effect effect = new AddCountersSourceEffect(CounterType.P1P1.createInstance(), GetXValue.instance);
        Ability ability = new ExhaustAbility(effect, cost);
        ability.addEffect(new SitaVarmaMaskedRacerMayEffect());
        this.addAbility(ability);
    }

    private SitaVarmaMaskedRacer(final SitaVarmaMaskedRacer card) {
        super(card);
    }

    @Override
    public SitaVarmaMaskedRacer copy() {
        return new SitaVarmaMaskedRacer(this);
    }
}

class SitaVarmaMaskedRacerMayEffect extends OneShotEffect {
    private static final String choiceText = "Have the base power and toughness of each other creature you control" +
            " become equal to Sita Varma's power until end of turn?";

    public SitaVarmaMaskedRacerMayEffect() {
        super(Outcome.Benefit);
        this.staticText = "Then you may have the base power and toughness of each other creature you control become equal to Sita Varma's power until end of turn.";
    }

    public SitaVarmaMaskedRacerMayEffect(final SitaVarmaMaskedRacerMayEffect effect) {
        super(effect);
    }

    @Override
    public SitaVarmaMaskedRacerMayEffect copy() {
        return new SitaVarmaMaskedRacerMayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || !controller.chooseUse(outcome, choiceText, source, game)) {
            return false;
        }
        ContinuousEffect setBasePowerToughnessEffect = new SetBasePowerToughnessAllEffect(SourcePermanentPowerValue.ALLOW_NEGATIVE,
                SourcePermanentPowerValue.ALLOW_NEGATIVE, Duration.EndOfTurn,
                StaticFilters.FILTER_OTHER_CONTROLLED_CREATURES);
        game.addEffect(setBasePowerToughnessEffect, source);
        return true;
    }
}
