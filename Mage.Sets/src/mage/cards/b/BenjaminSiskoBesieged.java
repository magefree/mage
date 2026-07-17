package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.PlayFromTopOfLibraryEffect;
import mage.abilities.effects.common.continuous.PlayWithTheTopCardRevealedEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class BenjaminSiskoBesieged extends CardImpl {

    private static final FilterCard filter = new FilterCard("play lands and cast creature spells");

    static {
        filter.add(Predicates.or(
            CardType.LAND.getPredicate(),
            CardType.CREATURE.getPredicate()
        ));
    }

    public BenjaminSiskoBesieged(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.OFFICER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Play with the top card of your library revealed.
        this.addAbility(new SimpleStaticAbility(new PlayWithTheTopCardRevealedEffect()));

        // You may play lands and cast creature spells from the top of your library.
        this.addAbility(new SimpleStaticAbility(new PlayFromTopOfLibraryEffect(filter)));
    }

    private BenjaminSiskoBesieged(final BenjaminSiskoBesieged card) {
        super(card);
    }

    @Override
    public BenjaminSiskoBesieged copy() {
        return new BenjaminSiskoBesieged(this);
    }
}
