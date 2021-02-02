
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.turn.SkipNextTurnSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class Chronatog extends CardImpl {

    public Chronatog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.ATOG);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {0}: Chronatog gets +3/+3 until end of turn. You skip your next turn. Activate this ability only once each turn.
        Ability ability = new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(3, 3, Duration.EndOfTurn), new GenericManaCost(0));
        ability.addEffect(new SkipNextTurnSourceEffect());
        this.addAbility(ability);
    }

    private Chronatog(final Chronatog card) {
        super(card);
    }

    @Override
    public Chronatog copy() {
        return new Chronatog(this);
    }
}
