
package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RemoveVariableCountersTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.OozeToken;
import mage.game.permanent.token.Token;

/**
 *
 * @author LevelX2
 */
public final class OozeFlux extends CardImpl {

    public OozeFlux(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{G}");

        // {1}{G}, Remove one or more +1/+1 counters from among creatures you control: Create an X/X green Ooze creature token, where X is the number of +1/+1 counters removed this way.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new OozeFluxCreateTokenEffect(new OozeToken()),new ManaCostsImpl<>("{1}{G}"));
        ability.addCost(new RemoveVariableCountersTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURES, CounterType.P1P1, "one or more", 1));
        this.addAbility(ability);
    }

    private OozeFlux(final OozeFlux card) {
        super(card);
    }

    @Override
    public OozeFlux copy() {
        return new OozeFlux(this);
    }
}

class OozeFluxCreateTokenEffect extends OneShotEffect {

    private final Token token;

    public OozeFluxCreateTokenEffect(Token token) {
        super(Outcome.PutCreatureInPlay);
        this.token = token;
        staticText = "Create an X/X green Ooze creature token, where X is the number of +1/+1 counters removed this way";
    }

    public OozeFluxCreateTokenEffect(final OozeFluxCreateTokenEffect effect) {
        super(effect);
        this.token = effect.token.copy();
    }

    @Override
    public OozeFluxCreateTokenEffect copy() {
        return new OozeFluxCreateTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = 0;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof RemoveVariableCountersTargetCost) {
                xValue = ((RemoveVariableCountersTargetCost) cost).getAmount();
                break;
            }
        }
        Token tokenCopy = token.copy();
        tokenCopy.getAbilities().newId();
        tokenCopy.getPower().setModifiedBaseValue(xValue);
        tokenCopy.getToughness().setModifiedBaseValue(xValue);
        tokenCopy.putOntoBattlefield(1, game, source, source.getControllerId());
        return true;
    }
}
