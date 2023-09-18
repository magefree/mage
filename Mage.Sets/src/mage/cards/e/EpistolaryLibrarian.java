package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.cost.CastFromHandForFreeEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicate;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EpistolaryLibrarian extends CardImpl {

    private static final FilterCard filter = new FilterCard("a spell with mana value X or less");

    static {
        filter.add(EpistolaryLibrarianPredicate.instance);
    }

    public EpistolaryLibrarian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");

        this.subtype.add(SubType.ASTARTES);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Veil of Time -- Whenever Epistolary Librarian attacks, you may cast a spell with mana value X or less from your hand without paying its mana cost, where X is the number of attacking creatures.
        Ability ability = new AttacksTriggeredAbility(new CastFromHandForFreeEffect(filter));
        ability.addEffect(new InfoEffect(", where X is the number of attacking creatures"));
        this.addAbility(ability.withFlavorWord("Veil of Time"));
    }

    private EpistolaryLibrarian(final EpistolaryLibrarian card) {
        super(card);
    }

    @Override
    public EpistolaryLibrarian copy() {
        return new EpistolaryLibrarian(this);
    }
}

enum EpistolaryLibrarianPredicate implements Predicate<Card> {
    instance;

    @Override
    public boolean apply(Card input, Game game) {
        return input.getManaValue() <= game
                .getBattlefield()
                .count(StaticFilters.FILTER_ATTACKING_CREATURE, null, null, game);
    }
}
