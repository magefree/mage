package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.ChannelAbility;
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
public final class SibaTrespassers extends CardImpl {

    public SibaTrespassers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.MOONFOLK);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Channel — {3}{U}, Discard Siba Trespassers: Tap up to two target creatures you don't control. Those creatures don't untap during their controller's next untap step.
        Ability ability = new ChannelAbility(
                "{3}{U}", new TapTargetEffect()
                .setText("tap up to two target creatures you don't control")
        );
        ability.addEffect(new DontUntapInControllersNextUntapStepTargetEffect("Those creatures"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.addAbility(ability);
    }

    private SibaTrespassers(final SibaTrespassers card) {
        super(card);
    }

    @Override
    public SibaTrespassers copy() {
        return new SibaTrespassers(this);
    }
}
