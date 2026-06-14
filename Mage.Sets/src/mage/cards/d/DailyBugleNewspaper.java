package mage.cards.d;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.TreasureToken;

/**
 *
 * @author muz
 */
public final class DailyBugleNewspaper extends CardImpl {

    public DailyBugleNewspaper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {2}, {T}: Draw a card, then discard a card. Create a Treasure token.
        Ability ability = new SimpleActivatedAbility(
            new DrawDiscardControllerEffect(1, 1), new GenericManaCost(2)
        );
        ability.addCost(new TapSourceCost());
        ability.addEffect(new CreateTokenEffect(new TreasureToken()));
        this.addAbility(ability);
    }

    private DailyBugleNewspaper(final DailyBugleNewspaper card) {
        super(card);
    }

    @Override
    public DailyBugleNewspaper copy() {
        return new DailyBugleNewspaper(this);
    }
}
