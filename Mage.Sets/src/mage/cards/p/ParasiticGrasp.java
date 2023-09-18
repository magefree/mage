package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.CleaveAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ParasiticGrasp extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent(SubType.HUMAN, "[Human] creature");

    public ParasiticGrasp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Cleave {1}{B}{B}
        Ability ability = new CleaveAbility(this, new DamageTargetEffect(3), "{1}{B}{B}");
        ability.addEffect(new GainLifeEffect(3));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Parasitic Grasp deals 3 damage to target [Human] creature. You gain 3 life.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addEffect(new GainLifeEffect(3));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private ParasiticGrasp(final ParasiticGrasp card) {
        super(card);
    }

    @Override
    public ParasiticGrasp copy() {
        return new ParasiticGrasp(this);
    }
}
