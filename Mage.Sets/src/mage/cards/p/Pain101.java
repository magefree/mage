package mage.cards.p;

import java.util.UUID;

import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class Pain101 extends CardImpl {

    public Pain101(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Until end of turn, target creature gains deathtouch and "When this creature dies, return it to the battlefield tapped under its owner's control."
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(DeathtouchAbility.getInstance())
                .setText("Until end of turn, target creature gains deathtouch")
        );
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                new DiesSourceTriggeredAbility(new ReturnSourceFromGraveyardToBattlefieldEffect(true, true), false),
                Duration.EndOfTurn,
                "and gains \"When this creature dies, return it to the battlefield tapped under its owner's control.\""
        ));
    }

    private Pain101(final Pain101 card) {
        super(card);
    }

    @Override
    public Pain101 copy() {
        return new Pain101(this);
    }
}
