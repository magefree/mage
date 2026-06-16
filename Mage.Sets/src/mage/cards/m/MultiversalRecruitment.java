package mage.cards.m;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author muz
 */
public final class MultiversalRecruitment extends CardImpl {

    public MultiversalRecruitment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");


        // Create a token that's a copy of target creature you control, except it isn't legendary.
        this.getSpellAbility().addEffect(
            new CreateTokenCopyTargetEffect()
                .setIsntLegendary(true)
                .setText("create a token that's a copy of target creature you control, except it isn't legendary")
        );
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        // Flashback {5}{U}{U}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{5}{U}{U}")));

    }

    private MultiversalRecruitment(final MultiversalRecruitment card) {
        super(card);
    }

    @Override
    public MultiversalRecruitment copy() {
        return new MultiversalRecruitment(this);
    }
}
