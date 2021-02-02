package mage.cards.s;

import mage.abilities.condition.common.CitysBlessingCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.AscendEffect;
import mage.abilities.hint.common.CitysBlessingHint;
import mage.abilities.hint.common.PermanentsYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SecretsOfTheGoldenCity extends CardImpl {

    public SecretsOfTheGoldenCity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}{U}");

        // Ascend
        this.getSpellAbility().addEffect(new AscendEffect());
        this.getSpellAbility().addHint(CitysBlessingHint.instance);
        this.getSpellAbility().addHint(PermanentsYouControlHint.instance);

        // Draw two cards. If you have the city's blessing, draw three cards instead.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(3),
                new DrawCardSourceControllerEffect(2),
                CitysBlessingCondition.instance,
                "Draw two cards. If you have the city's blessing, draw three cards instead"));
    }

    private SecretsOfTheGoldenCity(final SecretsOfTheGoldenCity card) {
        super(card);
    }

    @Override
    public SecretsOfTheGoldenCity copy() {
        return new SecretsOfTheGoldenCity(this);
    }
}
