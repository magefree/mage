package mage.cards.i;

import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InfuseWithVitality extends CardImpl {

    public InfuseWithVitality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}{G}");

        // Until end of turn, target creature gains deathtouch and "When this creature dies, return it to the battlefield tapped under its owner's control."
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                DeathtouchAbility.getInstance(), Duration.EndOfTurn
        ).setText("Until end of turn, target creature gains deathtouch"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                new DiesSourceTriggeredAbility(
                        new ReturnSourceFromGraveyardToBattlefieldEffect(true, true), false
                ), Duration.EndOfTurn, "and \"When this creature dies, " +
                "return it to the battlefield tapped under its owner's control.\""
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // You gain 2 life.
        this.getSpellAbility().addEffect(new GainLifeEffect(2).concatBy("<br>"));
    }

    private InfuseWithVitality(final InfuseWithVitality card) {
        super(card);
    }

    @Override
    public InfuseWithVitality copy() {
        return new InfuseWithVitality(this);
    }
}
