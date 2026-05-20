package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersPreparedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.effects.common.BecomePreparedSourceEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.PestBlackGreenAttacksToken;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LluwenExchangeStudent extends PrepareCard {

    public LluwenExchangeStudent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}", "Pest Friend", new CardType[]{CardType.SORCERY}, "{B/G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Lluwen enters prepared.
        this.addAbility(new EntersPreparedAbility());

        // Exile a creature card from your graveyard: Lluwen becomes prepared. Activate only as a sorcery.
        this.addAbility(new ActivateAsSorceryActivatedAbility(
                new BecomePreparedSourceEffect(),
                new ExileFromGraveCost(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_A))
        ));

        // Pest Friend
        // Sorcery {B/G}
        // Create a 1/1 black and green Pest creature token with "Whenever this creature attacks, you gain 1 life."
        this.getSpellCard().getSpellAbility().addEffect(new CreateTokenEffect(new PestBlackGreenAttacksToken()));
    }

    private LluwenExchangeStudent(final LluwenExchangeStudent card) {
        super(card);
    }

    @Override
    public LluwenExchangeStudent copy() {
        return new LluwenExchangeStudent(this);
    }
}
