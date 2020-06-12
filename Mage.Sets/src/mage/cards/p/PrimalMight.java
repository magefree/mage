

package mage.cards.p;

import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.effects.common.FightTargetsEffect;
import java.util.UUID;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetAnyTarget;

/**
 * @author mikalinn777
 */


public final class PrimalMight extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature you don't control");

    public PrimalMight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{G}");
        // Target creature gets +X/+X until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(ManacostVariableValue.instance, ManacostVariableValue.instance, Duration.EndOfTurn));
        // Then, it fights up to one target creature.
        this.getSpellAbility().addEffect(new FightTargetsEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(0, 1, filter, false));

    }

    public PrimalMight(final PrimalMight card) {
        super(card);
    }

    @Override
    public PrimalMight copy() {
        return new PrimalMight(this);
    }

}
