package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class ChaseSteinRunaway extends CardImpl {

    public ChaseSteinRunaway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {T}, Discard a card: Exile the top card of your library. Until the end of your next turn, you may play that card.
        Ability ability = new SimpleActivatedAbility(
            new ExileTopXMayPlayUntilEffect(1, Duration.UntilEndOfYourNextTurn), new TapSourceCost()
        );
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    private ChaseSteinRunaway(final ChaseSteinRunaway card) {
        super(card);
    }

    @Override
    public ChaseSteinRunaway copy() {
        return new ChaseSteinRunaway(this);
    }
}
