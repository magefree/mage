package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.KnightWhiteBlueToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChivalricAlliance extends CardImpl {

    public ChivalricAlliance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // Whenever you attack with two or more creatures, draw a card.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
                new DrawCardSourceControllerEffect(1), 2
        ));

        // {2}, Discard a card: Create a 2/2 white and blue Knight creature token with vigilance.
        Ability ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new KnightWhiteBlueToken()), new GenericManaCost(2)
        );
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    private ChivalricAlliance(final ChivalricAlliance card) {
        super(card);
    }

    @Override
    public ChivalricAlliance copy() {
        return new ChivalricAlliance(this);
    }
}
