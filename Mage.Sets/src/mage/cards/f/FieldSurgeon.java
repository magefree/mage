package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class FieldSurgeon extends CardImpl {

    public FieldSurgeon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Tap an untapped creature you control: Prevent the next 1 damage that would be dealt to target creature this turn.
        Ability ability = new SimpleActivatedAbility(
                new PreventDamageToTargetEffect(Duration.EndOfTurn, 1),
                new TapTargetCost(StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURE)
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private FieldSurgeon(final FieldSurgeon card) {
        super(card);
    }

    @Override
    public FieldSurgeon copy() {
        return new FieldSurgeon(this);
    }
}
