package mage.cards.r;

import java.util.UUID;

import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetOpponent;

/**
 * @author arcox
 */
public final class RunAfoul extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public RunAfoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Target opponent sacrifices a creature with flying.
        this.getSpellAbility().addEffect(new SacrificeEffect(filter, 1, "Target opponent"));
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private RunAfoul(final RunAfoul card) {
        super(card);
    }

    @Override
    public RunAfoul copy() {
        return new RunAfoul(this);
    }
}
