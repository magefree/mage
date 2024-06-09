package mage.cards.s;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SelfReflection extends CardImpl {

    public SelfReflection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}{U}");

        // Create a token that's a copy of target creature you control.
        this.getSpellAbility().addEffect(new CreateTokenCopyTargetEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        // Flashback {3}{U}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{3}{U}")));
    }

    private SelfReflection(final SelfReflection card) {
        super(card);
    }

    @Override
    public SelfReflection copy() {
        return new SelfReflection(this);
    }
}
