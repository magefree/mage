package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CuriousFarmAnimals extends CardImpl {

    public CuriousFarmAnimals(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.BOAR);
        this.subtype.add(SubType.ELK);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.OX);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When this creature dies, you gain 3 life.
        this.addAbility(new DiesSourceTriggeredAbility(new GainLifeEffect(3)));

        // {2}, Sacrifice this creature: Destroy up to one target artifact or enchantment.
        Ability ability = new SimpleActivatedAbility(new DestroyTargetEffect(), new GenericManaCost(2));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.addAbility(ability);
    }

    private CuriousFarmAnimals(final CuriousFarmAnimals card) {
        super(card);
    }

    @Override
    public CuriousFarmAnimals copy() {
        return new CuriousFarmAnimals(this);
    }
}
