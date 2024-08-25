package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.GainsChoiceOfAbilitiesEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class ButcherOfTheHorde extends CardImpl {

    public ButcherOfTheHorde(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}{B}");
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Sacrifice another creature: Butcher of the Horde gains your choice of vigilance, lifelink, or haste until end of turn.
        this.addAbility(new SimpleActivatedAbility(new GainsChoiceOfAbilitiesEffect(GainsChoiceOfAbilitiesEffect.TargetType.Source,
                VigilanceAbility.getInstance(), LifelinkAbility.getInstance(), HasteAbility.getInstance()),
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE)));
    }

    private ButcherOfTheHorde(final ButcherOfTheHorde card) {
        super(card);
    }

    @Override
    public ButcherOfTheHorde copy() {
        return new ButcherOfTheHorde(this);
    }
}
