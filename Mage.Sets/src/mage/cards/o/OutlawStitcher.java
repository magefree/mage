package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.PlotAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ZombieRogueToken;
import mage.watchers.common.CastSpellLastTurnWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class OutlawStitcher extends CardImpl {

    private static final Hint hint = new ValueHint("Spells you've cast this turn other than the first", OutlawStitcherDynamicValue.instance);

    public OutlawStitcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // When Outlaw Stitcher enters the battlefield, create a 2/2 blue and black Zombie Rogue creature token, then put two +1/+1 counters on that token for each spell you've cast this turn other than the first.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new OutlawStitcherEffect()).addHint(hint));

        // Plot {4}{U}
        this.addAbility(new PlotAbility("{4}{U}"));
    }

    private OutlawStitcher(final OutlawStitcher card) {
        super(card);
    }

    @Override
    public OutlawStitcher copy() {
        return new OutlawStitcher(this);
    }
}

enum OutlawStitcherDynamicValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int spellCastThisTurn = game.getState()
                .getWatcher(CastSpellLastTurnWatcher.class)
                .getAmountOfSpellsPlayerCastOnCurrentTurn(sourceAbility.getControllerId());
        if (spellCastThisTurn <= 1) {
            return 0;
        }
        return spellCastThisTurn - 1;
    }

    @Override
    public OutlawStitcherDynamicValue copy() {
        return this;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "spell you've cast this turn other than the first";
    }
}

class OutlawStitcherEffect extends OneShotEffect {

    OutlawStitcherEffect() {
        super(Outcome.PutCreatureInPlay);
    }

    private OutlawStitcherEffect(final OutlawStitcherEffect effect) {
        super(effect);
    }

    @Override
    public OutlawStitcherEffect copy() {
        return new OutlawStitcherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CreateTokenEffect effect = new CreateTokenEffect(new ZombieRogueToken());
        boolean result = effect.apply(game, source);
        int xvalue = OutlawStitcherDynamicValue.instance.calculate(game, source, this);
        if (xvalue <= 0 || !result) {
            return result;
        }
        for (UUID id : effect.getLastAddedTokenIds()) {
            Permanent token = game.getPermanent(id);
            if (token == null) {
                continue;
            }
            token.addCounters(CounterType.P1P1.createInstance(2 * xvalue), source.getControllerId(), source, game);
        }
        return true;
    }

}