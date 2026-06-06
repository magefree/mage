package mage.cards.g;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalPreventionEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.PreventAllDamageToSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GuardianNaga extends AdventureCard {

    public GuardianNaga(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SNAKE}, "{5}{W}{W}",
                "Banishing Coils",
                new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Guardian Naga
        this.getLeftHalfCard().setPT(5, 6);

        // Vigilance
        this.getLeftHalfCard().addAbility(VigilanceAbility.getInstance());

        // As long as it's your turn, prevent all damage that would be dealt to Guardian Naga.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(new ConditionalPreventionEffect(
                new PreventAllDamageToSourceEffect(Duration.WhileOnBattlefield), MyTurnCondition.instance,
                "during your turn, prevent all damage that would be dealt to {this}"
        )));

        // Banishing Coils
        // Exile target artifact or enchantment.
        this.getRightHalfCard().getSpellAbility().addEffect(new ExileTargetEffect());
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));

        finalizeCard();
    }

    private GuardianNaga(final GuardianNaga card) {
        super(card);
    }

    @Override
    public GuardianNaga copy() {
        return new GuardianNaga(this);
    }
}
