package mage.cards.b;

import java.util.UUID;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class BoneSplinters extends CardImpl {

    public BoneSplinters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // As an additional cost to cast Bone Splinters, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));
        // Destroy target creature.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().withChooseHint("to destroy"));
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
    }

    private BoneSplinters(final BoneSplinters card) {
        super(card);
    }

    @Override
    public BoneSplinters copy() {
        return new BoneSplinters(this);
    }
}
