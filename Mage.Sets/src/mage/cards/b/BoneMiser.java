package mage.cards.b;

import mage.MageInt;
import mage.Mana;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DiscardCardControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.permanent.token.ZombieToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BoneMiser extends CardImpl {

    private static final FilterCard filter = new FilterNonlandCard("a noncreature, nonland card");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
    }

    public BoneMiser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever you discard a creature card, create a 2/2 black Zombie creature token.
        this.addAbility(new DiscardCardControllerTriggeredAbility(
                new CreateTokenEffect(new ZombieToken()), false, StaticFilters.FILTER_CARD_CREATURE_A
        ));

        // Whenever you discard a land card, add {B}{B}.
        this.addAbility(new DiscardCardControllerTriggeredAbility(
                new BasicManaEffect(Mana.BlackMana(2)), false, StaticFilters.FILTER_CARD_LAND_A
        ));

        // Whenever you discard a noncreature, nonland card, draw a card.
        this.addAbility(new DiscardCardControllerTriggeredAbility(
                new DrawCardSourceControllerEffect(1), false, filter
        ));
    }

    private BoneMiser(final BoneMiser card) {
        super(card);
    }

    @Override
    public BoneMiser copy() {
        return new BoneMiser(this);
    }
}
