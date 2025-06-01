package mage.cards.a;

import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetAttackingCreature;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class ArmedResponse extends CardImpl {


    public ArmedResponse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Armed Response deals damage to target attacking creature equal to the number of Equipment you control.
        Effect effect = new DamageTargetEffect(new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_PERMANENT_EQUIPMENT));
        effect.setText("{this} deals damage to target attacking creature equal to the number of Equipment you control");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetAttackingCreature());
    }

    private ArmedResponse(final ArmedResponse card) {
        super(card);
    }

    @Override
    public ArmedResponse copy() {
        return new ArmedResponse(this);
    }
}
