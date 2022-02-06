
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.HeroicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class WavecrashTriton extends CardImpl {

    public WavecrashTriton(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Heroic - Whenever you cast a spell that targets Wavecrash Triton, tap target creature an opponent controls. That creature doesn't untap during its controller's next untap step.
        Ability ability = new HeroicAbility(new TapTargetEffect());
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));
        ability.addEffect(new DontUntapInControllersNextUntapStepTargetEffect("That creature"));
        this.addAbility(ability);
    }

    private WavecrashTriton(final WavecrashTriton card) {
        super(card);
    }

    @Override
    public WavecrashTriton copy() {
        return new WavecrashTriton(this);
    }
}
