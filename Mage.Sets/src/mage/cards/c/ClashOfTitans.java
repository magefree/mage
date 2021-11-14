package mage.cards.c;

import mage.abilities.effects.common.FightTargetsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ClashOfTitans extends CardImpl {

    public ClashOfTitans(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}{R}");

        // Target creature fights another target creature.
        this.getSpellAbility().addEffect(new FightTargetsEffect());
        Target target = new TargetCreaturePermanent();
        target.setTargetTag(1);
        this.getSpellAbility().addTarget(target);

        target = new TargetCreaturePermanent(StaticFilters.FILTER_ANOTHER_CREATURE_TARGET_2);
        target.setTargetTag(2);
        this.getSpellAbility().addTarget(target);
    }

    private ClashOfTitans(final ClashOfTitans card) {
        super(card);
    }

    @Override
    public ClashOfTitans copy() {
        return new ClashOfTitans(this);
    }
}
