package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class NullmageShepherd extends CardImpl {

    public NullmageShepherd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Tap four untapped creatures you control: Destroy target artifact or enchantment.
        Ability ability = new SimpleActivatedAbility(new DestroyTargetEffect(), new TapTargetCost(4, StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURES));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.addAbility(ability);
    }

    private NullmageShepherd(final NullmageShepherd card) {
        super(card);
    }

    @Override
    public NullmageShepherd copy() {
        return new NullmageShepherd(this);
    }
}
