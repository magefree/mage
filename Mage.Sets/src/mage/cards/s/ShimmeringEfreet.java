package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SourcePhaseInTriggeredAbility;
import mage.abilities.effects.common.PhaseOutTargetEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.PhasingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class ShimmeringEfreet extends CardImpl {

    public ShimmeringEfreet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.EFREET);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Phasing
        this.addAbility(PhasingAbility.getInstance());

        // Whenever Shimmering Efreet phases in, target creature phases out.
        Ability ability = new SourcePhaseInTriggeredAbility(new PhaseOutTargetEffect(), false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ShimmeringEfreet(final ShimmeringEfreet card) {
        super(card);
    }

    @Override
    public ShimmeringEfreet copy() {
        return new ShimmeringEfreet(this);
    }
}
