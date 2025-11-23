package mage.cards.e;

import mage.Mana;
import mage.abilities.effects.common.DamageTargetAndTargetEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ManaType;
import mage.filter.common.FilterAnyTarget;
import mage.filter.common.FilterPermanentOrPlayer;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetPermanentOrPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ExplosiveWelcome extends CardImpl {

    private static final FilterPermanentOrPlayer filter = new FilterAnyTarget("any other target");

    static {
        filter.getPermanentFilter().add(new AnotherTargetPredicate(2));
        filter.getPlayerFilter().add(new AnotherTargetPredicate(2));
    }

    public ExplosiveWelcome(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{7}{R}");

        // Explosive Welcome deals 5 damage to any target and 3 damage to any other target. Add {R}{R}{R}.
        this.getSpellAbility().addEffect(new DamageTargetAndTargetEffect(5, 3));
        this.getSpellAbility().addEffect(new BasicManaEffect(new Mana(ManaType.RED, 3)));
        this.getSpellAbility().addTarget(new TargetAnyTarget()
                .withChooseHint("to deal 5 damage").setTargetTag(1));
        this.getSpellAbility().addTarget(new TargetPermanentOrPlayer(filter)
                .withChooseHint("to deal 3 damage").setTargetTag(2));
    }

    private ExplosiveWelcome(final ExplosiveWelcome card) {
        super(card);
    }

    @Override
    public ExplosiveWelcome copy() {
        return new ExplosiveWelcome(this);
    }
}
