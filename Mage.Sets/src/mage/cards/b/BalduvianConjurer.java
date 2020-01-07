
package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterLandPermanent;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author TheElk801
 */
public final class BalduvianConjurer extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("snow land");

    static {
        filter.add(SuperType.SNOW.getPredicate());
    }

    public BalduvianConjurer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN, SubType.WIZARD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // {tap}: Target snow land becomes a 2/2 creature until end of turn. It's still a land.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureTargetEffect(new CreatureToken(2, 2), false, true, Duration.EndOfTurn), new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private BalduvianConjurer(final BalduvianConjurer card) {
        super(card);
    }

    @Override
    public BalduvianConjurer copy() {
        return new BalduvianConjurer(this);
    }
}
