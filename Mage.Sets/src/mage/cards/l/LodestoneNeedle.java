package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.keyword.ExploreTargetEffect;
import mage.abilities.keyword.CraftAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class LodestoneNeedle extends TransformingDoubleFacedCard {

    public LodestoneNeedle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{1}{U}",
                "Guidestone Compass",
                new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "U"
        );

        // Lodestone Needle
        // Flash
        this.getLeftHalfCard().addAbility(FlashAbility.getInstance());

        // When Lodestone Needle enters the battlefield, tap up to one target artifact or creature and put two stun counters on it.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect());
        ability.addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance(2))
                .setText("and put two stun counters on it"));
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));
        this.getLeftHalfCard().addAbility(ability);

        // Craft with artifact {2}{U}
        this.getLeftHalfCard().addAbility(new CraftAbility("{2}{U}"));

        // Guidestone Compass
        // {1}, {T}: Target creature you control explores. Activate only as a sorcery.
        Ability compassAbility = new ActivateAsSorceryActivatedAbility(new ExploreTargetEffect(), new GenericManaCost(1));
        compassAbility.addTarget(new TargetControlledCreaturePermanent());
        compassAbility.addCost(new TapSourceCost());
        this.getRightHalfCard().addAbility(compassAbility);
    }

    private LodestoneNeedle(final LodestoneNeedle card) {
        super(card);
    }

    @Override
    public LodestoneNeedle copy() {
        return new LodestoneNeedle(this);
    }
}
