
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.AftermathAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SpellAbilityType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class ClaimFame extends SplitCard {

    private static final FilterCard filter = new FilterCreatureCard("creature card with mana value 2 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }

    public ClaimFame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, new CardType[]{CardType.SORCERY}, "{B}", "{1}{R}", SpellAbilityType.SPLIT_AFTERMATH);

        // Claim
        // Return target creature card with converted mana cost 2 or less from your graveyard to the battlefield.
        getLeftHalfCard().getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        getLeftHalfCard().getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));

        // Fame
        // Aftermath
        getRightHalfCard().addAbility(new AftermathAbility().setRuleAtTheTop(true));
        // Target creature gets +2/+0 and gains haste until end of turn.
        getRightHalfCard().getSpellAbility().addEffect(new BoostTargetEffect(2, 0, Duration.EndOfTurn)
                .setText("target creature gets +2/+0"));
        getRightHalfCard().getSpellAbility().addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn)
                .setText("and gains haste until end of turn"));
        getRightHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());

    }

    private ClaimFame(final ClaimFame card) {
        super(card);
    }

    @Override
    public ClaimFame copy() {
        return new ClaimFame(this);
    }
}
