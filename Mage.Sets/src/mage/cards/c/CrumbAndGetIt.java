package mage.cards.c;

import mage.abilities.condition.common.GiftWasPromisedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.GiftAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.GiftType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CrumbAndGetIt extends CardImpl {

    public CrumbAndGetIt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Gift a Food
        this.addAbility(new GiftAbility(this, GiftType.FOOD));

        // Target creature you control gets +2/+2 until end of turn. If the gift was promised, that creature also gains indestructible until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new AddContinuousEffectToGame(new GainAbilityTargetEffect(IndestructibleAbility.getInstance())),
                GiftWasPromisedCondition.TRUE, "if the gift was promised, " +
                "that creature also gains indestructible until end of turn"
        ));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private CrumbAndGetIt(final CrumbAndGetIt card) {
        super(card);
    }

    @Override
    public CrumbAndGetIt copy() {
        return new CrumbAndGetIt(this);
    }
}
