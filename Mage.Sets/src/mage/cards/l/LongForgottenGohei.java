package mage.cards.l;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class LongForgottenGohei extends CardImpl {

    private static final FilterCard arcaneFilter = new FilterCard("Arcane spells");
    private static final FilterCreaturePermanent spiritFilter = new FilterCreaturePermanent("Spirit creatures");

    static {
        arcaneFilter.add(SubType.ARCANE.getPredicate());
        spiritFilter.add(SubType.SPIRIT.getPredicate());
    }

    public LongForgottenGohei(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // Arcane spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(arcaneFilter, 1)));

        // Spirit creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, spiritFilter, false)));
    }

    private LongForgottenGohei(final LongForgottenGohei card) {
        super(card);
    }

    @Override
    public LongForgottenGohei copy() {
        return new LongForgottenGohei(this);
    }
}
