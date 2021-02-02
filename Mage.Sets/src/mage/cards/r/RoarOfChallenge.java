package mage.cards.r;

import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByAllTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.common.FerociousHint;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class RoarOfChallenge extends CardImpl {

    public RoarOfChallenge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");


        // All creatures able to block target creature this turn do so.
        this.getSpellAbility().addEffect(new MustBeBlockedByAllTargetEffect(Duration.EndOfTurn));
        // <i>Ferocious</i> &mdash; That creature gains indestructible until end of turn if you control a creature with power 4 or greater.
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(
                new GainAbilityTargetEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn),
                new LockedInCondition(FerociousCondition.instance),
                "<br><i>Ferocious</i> &mdash; That creature gains indestructible until end of turn if you control a creature with power 4 or greater."));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addHint(FerociousHint.instance);
    }

    private RoarOfChallenge(final RoarOfChallenge card) {
        super(card);
    }

    @Override
    public RoarOfChallenge copy() {
        return new RoarOfChallenge(this);
    }
}
