
package mage.cards.m;

import java.util.UUID;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.OpponentControlsPermanentCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;

/**
 *
 * @author Plopman
 */
public final class Massacre extends CardImpl {

    private static final FilterPermanent filterPlains = new FilterPermanent();
    private static final FilterPermanent filterSwamp = new FilterPermanent();

    static {
        filterPlains.add(SubType.PLAINS.getPredicate());
        filterSwamp.add(SubType.SWAMP.getPredicate());
    }

    public Massacre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}{B}");


        // If an opponent controls a Plains and you control a Swamp, you may cast this spell without paying its mana cost.
        Condition condition = new CompoundCondition("If an opponent controls a Plains and you control a Swamp", 
                new OpponentControlsPermanentCondition(filterPlains),
                new PermanentsOnTheBattlefieldCondition(filterSwamp));
        this.addAbility(new AlternativeCostSourceAbility(null, condition));
        // All creatures get -2/-2 until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(-2, -2, Duration.EndOfTurn));
    }

    private Massacre(final Massacre card) {
        super(card);
    }

    @Override
    public Massacre copy() {
        return new Massacre(this);
    }
}
