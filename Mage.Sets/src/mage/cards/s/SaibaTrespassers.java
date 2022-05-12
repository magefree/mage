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
public final class SaibaTrespassers extends CardImpl {

    public SaibaTrespassers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.MOONFOLK);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Channel — {3}{U}, Discard Saiba Trespassers: Tap up to two target creatures you don't control. Those creatures don't untap during their controller's next untap step.
        Ability ability = new ChannelAbility("{3}{U}", new TapTargetEffect());
        ability.addEffect(new DontUntapInControllersNextUntapStepTargetEffect("Those creatures"));
        ability.addTarget(new TargetPermanent(0, 2, StaticFilters.FILTER_CREATURES_YOU_DONT_CONTROL));
        this.addAbility(ability);
    }

    private SaibaTrespassers(final SaibaTrespassers card) {
        super(card);
    }

    @Override
    public SaibaTrespassers copy() {
        return new SaibaTrespassers(this);
    }
}
