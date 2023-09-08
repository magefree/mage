

package mage.cards.o;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class OranRiefTheVastwood extends CardImpl {

    public OranRiefTheVastwood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},null);
        this.addAbility(new EntersBattlefieldTappedAbility());
        this.addAbility(new GreenManaAbility());
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new OranRiefTheVastwoodEffect(), new TapSourceCost()));
    }

    private OranRiefTheVastwood(final OranRiefTheVastwood card) {
        super(card);
    }

    @Override
    public OranRiefTheVastwood copy() {
        return new OranRiefTheVastwood(this);
    }

}

class OranRiefTheVastwoodEffect extends OneShotEffect {

    public OranRiefTheVastwoodEffect() {
        super(Outcome.BoostCreature);
        staticText = "Put a +1/+1 counter on each green creature that entered the battlefield this turn";
    }

    private OranRiefTheVastwoodEffect(final OranRiefTheVastwoodEffect effect) {
        super(effect);
    }

    @Override
    public OranRiefTheVastwoodEffect copy() {
        return new OranRiefTheVastwoodEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterPermanent filter = new FilterPermanent();
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(new ColorPredicate(ObjectColor.GREEN));
        for (Permanent permanent: game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
            if (permanent.getTurnsOnBattlefield() == 0) {
                permanent.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
            }
        }
        return true;
    }

}