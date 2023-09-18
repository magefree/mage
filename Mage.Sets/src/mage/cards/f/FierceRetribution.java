package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.CleaveAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAttackingCreature;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FierceRetribution extends CardImpl {

    public FierceRetribution(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Cleave {5}{W}
        Ability ability = new CleaveAbility(this, new DestroyTargetEffect(), "{5}{W}");
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Destroy target [attacking] creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect().setText("destroy target [attacking] creature"));
        this.getSpellAbility().addTarget(new TargetAttackingCreature());
    }

    private FierceRetribution(final FierceRetribution card) {
        super(card);
    }

    @Override
    public FierceRetribution copy() {
        return new FierceRetribution(this);
    }
}
