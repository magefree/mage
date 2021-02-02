
package mage.cards.h;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class HarmonicSliver extends CardImpl {

    private static final FilterCreaturePermanent filterSliver = new FilterCreaturePermanent();
    static {
        filterSliver.add(SubType.SLIVER.getPredicate());
    }

    public HarmonicSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{W}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // All Slivers have "When this permanent enters the battlefield, destroy target artifact or enchantment."
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), false);
        TargetPermanent target = new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT);
        ability.addTarget(target);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(
                ability, Duration.WhileOnBattlefield,
                filterSliver, "All Slivers have \"When this permanent enters the battlefield, destroy target artifact or enchantment.\"")));
    }

    private HarmonicSliver(final HarmonicSliver card) {
        super(card);
    }

    @Override
    public HarmonicSliver copy() {
        return new HarmonicSliver(this);
    }
}
