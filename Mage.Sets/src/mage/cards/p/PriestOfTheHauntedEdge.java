package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PriestOfTheHauntedEdge extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledLandPermanent("snow lands you control");

    static {
        filter.add(SuperType.SNOW.getPredicate());
    }

    private static final DynamicValue xValue = new SignInversionDynamicValue(new PermanentsOnBattlefieldCount(filter, null));

    public PriestOfTheHauntedEdge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.supertype.add(SuperType.SNOW);
        this.subtype.add(SubType.ZOMBIE, SubType.CLERIC);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // {T}, Sacrifice Priest of the Haunted Edge: Target creature gets -X/-X until end of turn,
        // where X is the number of snow lands you control. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn),
                new TapSourceCost()
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private PriestOfTheHauntedEdge(final PriestOfTheHauntedEdge card) {
        super(card);
    }

    @Override
    public PriestOfTheHauntedEdge copy() {
        return new PriestOfTheHauntedEdge(this);
    }
}
