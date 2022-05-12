package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromEverywhereSourceCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RoccoCabarettiCaterer extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature card with mana value X or less");

    static {
        filter.add(RoccoCabarettiCatererPredicate.instance);
    }

    public RoccoCabarettiCaterer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{R}{G}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When Rocco, Cabaretti Caterer enters the battlefield, if you cast it, you may search your library for a creature card with mana value X or less, put it onto the battlefield, then shuffle.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(
                        new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter)),
                        true
                ),
                CastFromEverywhereSourceCondition.instance,
                "When {this} enters the battlefield, " +
                "if you cast it, you may search your library for a creature card with mana value X or less, " +
                "put it onto the battlefield, then shuffle.")
        );
    }

    private RoccoCabarettiCaterer(final RoccoCabarettiCaterer card) {
        super(card);
    }

    @Override
    public RoccoCabarettiCaterer copy() {
        return new RoccoCabarettiCaterer(this);
    }
}

enum RoccoCabarettiCatererPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        if (input.getSource() == null) {
            return false;
        }
        return input.getObject().getManaValue()
                <= ManacostVariableValue.ETB.calculate(game, input.getSource(), null);
    }
}
