package mage.cards.c;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.HarmonizeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChanneledDragonfire extends CardImpl {

    public ChanneledDragonfire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // Channeled Dragonfire deals 2 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetAnyTarget());

        // Harmonize {5}{R}{R}
        this.addAbility(new HarmonizeAbility(this, "{5}{R}{R}"));
    }

    private ChanneledDragonfire(final ChanneledDragonfire card) {
        super(card);
    }

    @Override
    public ChanneledDragonfire copy() {
        return new ChanneledDragonfire(this);
    }
}
