package mage.cards.r;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AffinityEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.LoseAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RebelSalvo extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent(SubType.EQUIPMENT, "Equipment");

    public RebelSalvo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Affinity for Equipment
        this.addAbility(new SimpleStaticAbility(new AffinityEffect(filter)));

        // Rebel Salvo deals 5 damage to target creature or planeswalker. That permanent loses indestructible unil end of turn.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
        this.getSpellAbility().addEffect(new LoseAbilityTargetEffect(IndestructibleAbility.getInstance())
                .setText("that permanent loses indestructible unil end of turn"));
    }

    private RebelSalvo(final RebelSalvo card) {
        super(card);
    }

    @Override
    public RebelSalvo copy() {
        return new RebelSalvo(this);
    }
}
