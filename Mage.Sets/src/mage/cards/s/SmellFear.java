package mage.cards.s;

import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SmellFear extends CardImpl {

    public SmellFear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Proliferate.
        this.getSpellAbility().addEffect(new ProliferateEffect());

        // Target creature you control fights up to one target creature you don't control.
        this.getSpellAbility().addEffect(new FightTargetsEffect().setText(
                "<br>Target creature you control fights up to one target creature you don't control"
        ));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1, StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL, false));
    }

    private SmellFear(final SmellFear card) {
        super(card);
    }

    @Override
    public SmellFear copy() {
        return new SmellFear(this);
    }
}
