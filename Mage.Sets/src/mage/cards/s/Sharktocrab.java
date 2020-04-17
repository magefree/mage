package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.OneOrMoreCountersAddedTriggeredAbility;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.AdaptAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Sharktocrab extends CardImpl {

    public Sharktocrab(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");

        this.subtype.add(SubType.SHARK);
        this.subtype.add(SubType.OCTOPUS);
        this.subtype.add(SubType.CRAB);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {2}{G}{U}: Adapt 1.
        this.addAbility(new AdaptAbility(1, "{2}{G}{U}"));

        // Whenever one or more +1/+1 counter are put on Sharktocrab, tap target creature an opponent controls. That creature doesn't untap during its controller's next untap step.
        Ability ability = new OneOrMoreCountersAddedTriggeredAbility(new TapTargetEffect());
        ability.addEffect(new DontUntapInControllersNextUntapStepTargetEffect("That creature"));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private Sharktocrab(final Sharktocrab card) {
        super(card);
    }

    @Override
    public Sharktocrab copy() {
        return new Sharktocrab(this);
    }
}
