package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.common.LessonsInGraveCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChakraMeditation extends CardImpl {

    public ChakraMeditation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // When this enchantment enters, return up to one target instant or sorcery card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(0, 1, StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY_FROM_YOUR_GRAVEYARD));
        this.addAbility(ability);

        // Whenever you cast an instant or sorcery spell, draw a card. Then discard a card unless there are three or more Lesson cards in your graveyard.
        ability = new SpellCastControllerTriggeredAbility(
                new DrawCardSourceControllerEffect(1),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        );
        ability.addEffect(new ConditionalOneShotEffect(
                null, new DiscardControllerEffect(1), LessonsInGraveCondition.THREE,
                "Then discard a card unless there are three or more Lesson cards in your graveyard"
        ));
        this.addAbility(ability.addHint(LessonsInGraveCondition.getHint()));
    }

    private ChakraMeditation(final ChakraMeditation card) {
        super(card);
    }

    @Override
    public ChakraMeditation copy() {
        return new ChakraMeditation(this);
    }
}
