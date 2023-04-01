package mage.cards.i;

import mage.abilities.common.DrawCardTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.FaerieToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ImprobableAlliance extends CardImpl {

    public ImprobableAlliance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}{R}");

        // Whenever you draw your second card each turn, create a 1/1 blue Faerie creature token with flying.
        this.addAbility(new DrawCardTriggeredAbility(new CreateTokenEffect(new FaerieToken()), false, 2));

        // {4}{U}{R}: Draw a card, then discard a card.
        this.addAbility(new SimpleActivatedAbility(
                new DrawDiscardControllerEffect(1, 1), new ManaCostsImpl<>("{4}{U}{R}")
        ));
    }

    private ImprobableAlliance(final ImprobableAlliance card) {
        super(card);
    }

    @Override
    public ImprobableAlliance copy() {
        return new ImprobableAlliance(this);
    }
}
