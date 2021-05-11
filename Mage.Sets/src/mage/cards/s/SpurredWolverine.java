
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class SpurredWolverine extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("untapped Beasts you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(SubType.BEAST.getPredicate());
    }

    public SpurredWolverine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.WOLVERINE);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Tap two untapped Beasts you control: Target creature gains first strike until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityTargetEffect(
            FirstStrikeAbility.getInstance(), Duration.EndOfTurn), new TapTargetCost(new TargetControlledPermanent(2, 2, filter, false)));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SpurredWolverine(final SpurredWolverine card) {
        super(card);
    }

    @Override
    public SpurredWolverine copy() {
        return new SpurredWolverine(this);
    }
}
