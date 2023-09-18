package mage.cards.s;

import java.util.UUID;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.TargetManaValue;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author weirddan455
 */
public final class SheoldredsRestoration extends CardImpl {

    public SheoldredsRestoration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // Kicker {2}{W}
        this.addAbility(new KickerAbility("{2}{W}"));

        // Return target creature card from your graveyard to the battlefield.
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());

        // If this spell was kicked, you gain life equal to that card's mana value. Otherwise, you lose that much life.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new GainLifeEffect(TargetManaValue.instance),
                new LoseLifeSourceControllerEffect(TargetManaValue.instance),
                KickedCondition.ONCE,
                "If this spell was kicked, you gain life equal to that card's mana value. Otherwise, you lose that much life."
        ));

        // Exile Sheoldred's Restoration.
        this.getSpellAbility().addEffect(new ExileSpellEffect().concatBy("<br>"));
    }

    private SheoldredsRestoration(final SheoldredsRestoration card) {
        super(card);
    }

    @Override
    public SheoldredsRestoration copy() {
        return new SheoldredsRestoration(this);
    }
}
