package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.CraftAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class LodestoneNeedle extends CardImpl {

    public LodestoneNeedle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{U}");
        this.secondSideCardClazz = mage.cards.g.GuidestoneCompass.class;

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Lodestone Needle enters the battlefield, tap up to one target artifact or creature and put two stun counters on it.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect());
        ability.addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance(2))
                .setText("and put two stun counters on it"));
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));
        this.addAbility(ability);

        // Craft with artifact {2}{U}
        this.addAbility(new CraftAbility("{2}{U}"));
    }

    private LodestoneNeedle(final LodestoneNeedle card) {
        super(card);
    }

    @Override
    public LodestoneNeedle copy() {
        return new LodestoneNeedle(this);
    }
}
