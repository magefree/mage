package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DiscardCardControllerTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MaryReadAndAnneBonny extends CardImpl {

    private static final FilterCard filter = new FilterCard("an Island, Pirate, or Vehicle card");

    static {
        filter.add(Predicates.or(
                SubType.ISLAND.getPredicate(),
                SubType.PIRATE.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
    }

    public MaryReadAndAnneBonny(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {T}: Draw a card, then discard a card.
        this.addAbility(new SimpleActivatedAbility(new DrawDiscardControllerEffect(), new TapSourceCost()));

        // Whenever you discard an Island, Pirate, or Vehicle card, create a tapped Treasure token.
        this.addAbility(new DiscardCardControllerTriggeredAbility(
                new CreateTokenEffect(new TreasureToken(), 1, true), false, filter
        ));
    }

    private MaryReadAndAnneBonny(final MaryReadAndAnneBonny card) {
        super(card);
    }

    @Override
    public MaryReadAndAnneBonny copy() {
        return new MaryReadAndAnneBonny(this);
    }
}
