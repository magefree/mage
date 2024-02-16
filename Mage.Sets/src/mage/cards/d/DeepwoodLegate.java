
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.OpponentControlsPermanentCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;

/**
 *
 * @author fireshoes
 */
public final class DeepwoodLegate extends CardImpl {

    private static final FilterPermanent filterForest = new FilterPermanent();
    private static final FilterPermanent filterSwamp = new FilterPermanent();

    static {
        filterForest.add(SubType.FOREST.getPredicate());
        filterSwamp.add(SubType.SWAMP.getPredicate());
    }

    public DeepwoodLegate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.SHADE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // If an opponent controls a Forest and you control a Swamp, you may cast this spell without paying its mana cost.
        Condition condition = new CompoundCondition("If an opponent controls a Forest and you control a Swamp",
                new OpponentControlsPermanentCondition(filterForest),
                new PermanentsOnTheBattlefieldCondition(filterSwamp));
        this.addAbility(new AlternativeCostSourceAbility(null, condition));

        // {B}: Deepwood Legate gets +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 1, Duration.EndOfTurn), new ManaCostsImpl<>("{B}")));
    }

    private DeepwoodLegate(final DeepwoodLegate card) {
        super(card);
    }

    @Override
    public DeepwoodLegate copy() {
        return new DeepwoodLegate(this);
    }
}
