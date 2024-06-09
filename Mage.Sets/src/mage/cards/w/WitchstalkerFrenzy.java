package mage.cards.w;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CreaturesThatAttackedThisTurnCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Susucr
 */
public final class WitchstalkerFrenzy extends CardImpl {

    public WitchstalkerFrenzy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}");
        
        // This spell costs {1} less to cast for each creature that attacked this turn.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(CreaturesThatAttackedThisTurnCount.instance)
                .setText("this spell costs {1} less to cast for each creature that attacked this turn")
        ).addHint(CreaturesThatAttackedThisTurnCount.instance.getHint()).setRuleAtTheTop(true));

        // Witchstalker Frenzy deals 5 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private WitchstalkerFrenzy(final WitchstalkerFrenzy card) {
        super(card);
    }

    @Override
    public WitchstalkerFrenzy copy() {
        return new WitchstalkerFrenzy(this);
    }
}
