package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTargetAnyTriggeredAbility;
import mage.abilities.effects.common.counter.AddPoisonCounterTargetEffect;
import mage.abilities.keyword.ToxicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class VeneratedRotpriest extends CardImpl {

    public VeneratedRotpriest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Toxic 1
        this.addAbility(new ToxicAbility(1));

        // Whenever a creature you control becomes the target of a spell, target opponent gets a poison counter.
        Ability ability = new BecomesTargetAnyTriggeredAbility(new AddPoisonCounterTargetEffect(1),
                StaticFilters.FILTER_CONTROLLED_A_CREATURE, StaticFilters.FILTER_SPELL_A, SetTargetPointer.NONE, false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private VeneratedRotpriest(final VeneratedRotpriest card) {
        super(card);
    }

    @Override
    public VeneratedRotpriest copy() {
        return new VeneratedRotpriest(this);
    }
}
