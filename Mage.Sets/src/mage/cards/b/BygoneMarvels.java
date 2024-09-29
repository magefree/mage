package mage.cards.b;

import mage.abilities.condition.common.DescendCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.CopySourceSpellEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BygoneMarvels extends CardImpl {

    private static final FilterCard filter = new FilterPermanentCard("permanent card from your graveyard");

    public BygoneMarvels(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}{G}");

        // Descend 8 -- When you cast this spell, if there are eight or more permanent cards in your graveyard, copy this spell twice. You may choose new targets for the copies.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new CastSourceTriggeredAbility(new CopySourceSpellEffect(2)),
                DescendCondition.EIGHT, "When you cast this spell, if there are eight or more " +
                "permanent cards in your graveyard, copy this spell twice. You may choose new targets for the copies."
        ).setAbilityWord(AbilityWord.DESCEND_8).addHint(DescendCondition.getHint()));

        // Return target permanent card from your graveyard to your hand. Exile Bygone Marvels.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addEffect(new ExileSpellEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));
    }

    private BygoneMarvels(final BygoneMarvels card) {
        super(card);
    }

    @Override
    public BygoneMarvels copy() {
        return new BygoneMarvels(this);
    }
}
