package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.HasteGolemToken;
import mage.game.permanent.token.Token;

/**
 *
 * @author @stwalsh4118
 */
public final class VulshokFactory extends CardImpl {
    
    private static final DynamicValue xValue = new CountersSourceCount(CounterType.CHARGE);


    public VulshokFactory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{R}");
        

        // {T}: Add {R}. Put a charge counter on Vulshok Factory.
        Ability ability = new RedManaAbility();
        ability.addEffect(new AddCountersSourceEffect(CounterType.CHARGE.createInstance(1)));
        this.addAbility(ability);

        // {2}{R}, {T}, Sacrifice Vulshok Factory: Create an X/X colorless Golem artifact creature token with haste, where X is the number of charge counters on Vulshok Factory. Activate only as a sorcery.
        Ability sacAbility = new ActivateAsSorceryActivatedAbility(new VulshokFactoryEffect(xValue), new ManaCostsImpl<>("{2}{R}"));
        sacAbility.addCost(new TapSourceCost());
        sacAbility.addCost(new SacrificeSourceCost());
        this.addAbility(sacAbility);
    }

    private VulshokFactory(final VulshokFactory card) {
        super(card);
    }

    @Override
    public VulshokFactory copy() {
        return new VulshokFactory(this);
    }

}

class VulshokFactoryEffect extends OneShotEffect {

    private final DynamicValue xValue;

    VulshokFactoryEffect(DynamicValue xValue) {
        super(Outcome.Benefit);
        staticText = "create an X/X colorless Golem artifact creature token with haste, where X is the number of charge counters on {this}";
        this.xValue = xValue;
    }

    private VulshokFactoryEffect(final VulshokFactoryEffect effect) {
        super(effect);
        this.xValue = effect.xValue;
    }

    @Override
    public VulshokFactoryEffect copy() {
        return new VulshokFactoryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new HasteGolemToken(xValue.calculate(game, source, this));
        return token.putOntoBattlefield(1, game, source);
    }
}

