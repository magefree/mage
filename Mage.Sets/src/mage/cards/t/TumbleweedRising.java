package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.GreatestPowerAmongControlledCreaturesValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.PlotAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.SeedGuardianToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TumbleweedRising extends CardImpl {

    public TumbleweedRising(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Create an X/X green Elemental creature token, where X is the greatest power among creatures you control.
        this.getSpellAbility().addEffect(new TumbleweedRisingEffect());
        this.getSpellAbility().addHint(GreatestPowerAmongControlledCreaturesValue.getHint());

        // Plot {2}{G}
        this.addAbility(new PlotAbility("{2}{G}"));
    }

    private TumbleweedRising(final TumbleweedRising card) {
        super(card);
    }

    @Override
    public TumbleweedRising copy() {
        return new TumbleweedRising(this);
    }
}


class TumbleweedRisingEffect extends OneShotEffect {

    TumbleweedRisingEffect() {
        super(Outcome.Benefit);
        staticText = "Create an X/X green Elemental creature token, "
                + "where X is the greatest power among creatures you control";
    }

    private TumbleweedRisingEffect(final TumbleweedRisingEffect effect) {
        super(effect);
    }

    @Override
    public TumbleweedRisingEffect copy() {
        return new TumbleweedRisingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int xvalue = GreatestPowerAmongControlledCreaturesValue.instance.calculate(game, source, this);
        return new CreateTokenEffect(new SeedGuardianToken(xvalue)).apply(game, source);
    }
}
