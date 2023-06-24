package mage.cards.w;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.SpliceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.Target;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class WearAway extends CardImpl {

    public WearAway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}{G}");
        this.subtype.add(SubType.ARCANE);

        // Destroy target artifact or enchantment.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        Target target = new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT).withChooseHint("destroy");
        this.getSpellAbility().addTarget(target);
        // Splice onto Arcane {3}{G}
        this.addAbility(new SpliceAbility(SpliceAbility.ARCANE, "{3}{G}"));
    }

    private WearAway(final WearAway card) {
        super(card);
    }

    @Override
    public WearAway copy() {
        return new WearAway(this);
    }
}
