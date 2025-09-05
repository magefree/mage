package mage.cards.e;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.MayhemAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElectrosBolt extends CardImpl {

    public ElectrosBolt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Electro's Bolt deals 4 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Mayhem {1}{R}
        this.addAbility(new MayhemAbility(this, "{1}{R}"));
    }

    private ElectrosBolt(final ElectrosBolt card) {
        super(card);
    }

    @Override
    public ElectrosBolt copy() {
        return new ElectrosBolt(this);
    }
}
