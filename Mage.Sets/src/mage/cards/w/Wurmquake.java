package mage.cards.w;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.FlashbackAbility;
import mage.abilities.keyword.ToxicAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.abilities.dynamicvalue.common.ManaSpentToCastCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.token.TokenImpl;

/**
 *
 * @author @stwalsh4118
 */
public final class Wurmquake extends CardImpl {

    public Wurmquake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}{G}");
        

        // Corrupted -- Create an X/X green Phyrexian Wurm creature token with trample and toxic 1, where X is the amount of mana spent to cast this spell. Then for each opponent with three or more poison counters, you create another one of those tokens.
        this.getSpellAbility().addEffect(new WurmquakeEffect());

        // Flashback {8}{G}{G}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{8}{G}{G}")));

    }

    private Wurmquake(final Wurmquake card) {
        super(card);
    }

    @Override
    public Wurmquake copy() {
        return new Wurmquake(this);
    }
}

class WurmquakeEffect extends OneShotEffect {

    public WurmquakeEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = AbilityWord.CORRUPTED.formatWord() + "Create an X/X green Phyrexian Wurm creature token with trample and toxic 1, where X is the amount of mana spent to cast this spell. Then for each opponent with three or more poison counters, you create another one of those tokens.";
    }

    public WurmquakeEffect(final WurmquakeEffect effect) {
        super(effect);
    }

    @Override
    public WurmquakeEffect copy() {
        return new WurmquakeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = ManaSpentToCastCount.instance.calculate(game, source, this);
        new CreateTokenEffect(new XXWurmToken(xValue)).apply(game, source);
        int amount = 0;
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            if (game.getPlayer(opponentId).getCounters().getCount(CounterType.POISON) >= 3) {
                amount++;
            }
        }

        new CreateTokenEffect(new XXWurmToken(xValue), amount).apply(game, source);

        return true;
    }
}

class XXWurmToken extends TokenImpl {

    public XXWurmToken(int amount) {
        super("Wurm", "X/X green Phyrexian Wurm creature token with trample and toxic 1");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.PHYREXIAN);
        subtype.add(SubType.WURM);
        power = new MageInt(amount);
        toughness = new MageInt(amount);
        addAbility(TrampleAbility.getInstance());
        addAbility(new ToxicAbility(1));
    }

    public XXWurmToken(final XXWurmToken token) {
        super(token);
    }

    public XXWurmToken copy() {
        return new XXWurmToken(this);
    }
}
