package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.FractalToken;
import mage.game.permanent.token.Token;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FractalHarness extends CardImpl {

    public FractalHarness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{X}{2}{G}");

        this.subtype.add(SubType.EQUIPMENT);

        // When Fractal Harness enters the battlefield, create a 0/0 green and blue Fractal creature token. Put X +1/+1 counters on it and attach Fractal Harness to it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new FractalHarnessTokenEffect()));

        // Whenever equipped creature attacks, double the number of +1/+1 counters on it.
        this.addAbility(new AttacksAttachedTriggeredAbility(
                new FractalHarnessDoubleEffect(), AttachmentType.EQUIPMENT, false
        ));

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private FractalHarness(final FractalHarness card) {
        super(card);
    }

    @Override
    public FractalHarness copy() {
        return new FractalHarness(this);
    }
}

class FractalHarnessTokenEffect extends OneShotEffect {

    FractalHarnessTokenEffect() {
        super(Outcome.Benefit);
        staticText = "create a 0/0 green and blue Fractal creature token. "
                + "Put X +1/+1 counters on it and attach {this} to it";
    }

    private FractalHarnessTokenEffect(final FractalHarnessTokenEffect effect) {
        super(effect);
    }

    @Override
    public FractalHarnessTokenEffect copy() {
        return new FractalHarnessTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new FractalToken();
        token.putOntoBattlefield(1, game, source, source.getControllerId());
        int xValue = ManacostVariableValue.ETB.calculate(game, source, this);
        boolean flag = true;
        for (UUID tokenId : token.getLastAddedTokenIds()) {
            Permanent permanent = game.getPermanent(tokenId);
            if (permanent == null) {
                continue;
            }
            if (flag
                    && permanent.addAttachment(source.getSourceId(), source, game)) {
                flag = false;
            }
            permanent.addCounters(CounterType.P1P1.createInstance(xValue), source.getControllerId(), source, game);
        }
        return true;
    }
}

class FractalHarnessDoubleEffect extends OneShotEffect {

    FractalHarnessDoubleEffect() {
        super(Outcome.Benefit);
        staticText = "double the number of +1/+1 counters on it";
    }

    private FractalHarnessDoubleEffect(final FractalHarnessDoubleEffect effect) {
        super(effect);
    }

    @Override
    public FractalHarnessDoubleEffect copy() {
        return new FractalHarnessDoubleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent((UUID) getValue("sourceId"));
        if (permanent == null) {
            return false;
        }
        return permanent.addCounters(CounterType.P1P1.createInstance(permanent.getCounters(game).getCount(CounterType.P1P1)),
                 source.getControllerId(), source, game);
    }
}
