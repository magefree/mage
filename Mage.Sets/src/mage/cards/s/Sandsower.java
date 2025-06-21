package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class Sandsower extends CardImpl {

    public Sandsower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Tap three untapped creatures you control: Tap target creature.
        Ability ability = new SimpleActivatedAbility(new TapTargetEffect(), new TapTargetCost(3, StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURES));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private Sandsower(final Sandsower card) {
        super(card);
    }

    @Override
    public Sandsower copy() {
        return new Sandsower(this);
    }
}
