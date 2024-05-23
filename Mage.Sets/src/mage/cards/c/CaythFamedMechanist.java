package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.PopulateEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.abilities.keyword.FabricateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CaythFamedMechanist extends CardImpl {

    public CaythFamedMechanist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Fabricate 1
        this.addAbility(new FabricateAbility(1));

        // Other nontoken creatures you control have fabricate 1.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new FabricateAbility(1), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_CREATURES_NON_TOKEN
        )));

        // {2}, {T}: Choose one --
        // * Populate.
        Ability ability = new SimpleActivatedAbility(new PopulateEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());

        // * Proliferate.
        ability.addMode(new Mode(new ProliferateEffect()));
        this.addAbility(ability);
    }

    private CaythFamedMechanist(final CaythFamedMechanist card) {
        super(card);
    }

    @Override
    public CaythFamedMechanist copy() {
        return new CaythFamedMechanist(this);
    }
}
