package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.keyword.IslandcyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TidalTerror extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledCreaturePermanent("other untapped creatures you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(AnotherPredicate.instance);
    }

    public TidalTerror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");

        this.subtype.add(SubType.OCTOPUS);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Whenever Tidal Terror attacks, you may tap two other untapped creatures you control. If you do, Tidal Terror can't be blocked this turn.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(
                new CantBeBlockedSourceEffect(Duration.EndOfTurn),
                new TapTargetCost(new TargetControlledPermanent(2, filter))
        )));

        // Islandcycling {2}
        this.addAbility(new IslandcyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private TidalTerror(final TidalTerror card) {
        super(card);
    }

    @Override
    public TidalTerror copy() {
        return new TidalTerror(this);
    }
}
