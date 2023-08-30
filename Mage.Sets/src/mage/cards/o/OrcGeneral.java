package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class OrcGeneral extends CardImpl {
    
    private static final FilterControlledPermanent filterOrcOrGoblin = new FilterControlledPermanent("another Orc or Goblin");
    private static final FilterCreaturePermanent filterOrc = new FilterCreaturePermanent("Orc creatures");
    
    static {
        filterOrcOrGoblin.add(Predicates.or(SubType.ORC.getPredicate(),
                SubType.GOBLIN.getPredicate()));
        filterOrcOrGoblin.add(AnotherPredicate.instance);
        filterOrc.add(SubType.ORC.getPredicate());
    }

    public OrcGeneral(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {tap}, Sacrifice another Orc or Goblin: Other Orc creatures get +1/+1 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.EndOfTurn, filterOrc, true), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filterOrcOrGoblin)));
        this.addAbility(ability);
    }

    private OrcGeneral(final OrcGeneral card) {
        super(card);
    }

    @Override
    public OrcGeneral copy() {
        return new OrcGeneral(this);
    }
}
