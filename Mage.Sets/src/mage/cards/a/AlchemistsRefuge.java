
package mage.cards.a;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

/**
 *
 * @author noxx
 *
 */
public final class AlchemistsRefuge extends CardImpl {

    private static final FilterCard filter = new FilterCard("spells");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public AlchemistsRefuge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {G}{U}, {tap}: You may cast spells this turn as though they had flash.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new AddContinuousEffectToGame(new CastAsThoughItHadFlashAllEffect(Duration.EndOfTurn, filter)),
                new CompositeCost(new ManaCostsImpl<>("{G}{U}"), new TapSourceCost(), "{G}{U}, {T}")));
    }

    private AlchemistsRefuge(final AlchemistsRefuge card) {
        super(card);
    }

    @Override
    public AlchemistsRefuge copy() {
        return new AlchemistsRefuge(this);
    }
}
