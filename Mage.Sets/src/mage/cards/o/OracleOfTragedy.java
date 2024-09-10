package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldOrDiesSourceTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.ShuffleIntoLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OracleOfTragedy extends CardImpl {

    private static final FilterCard filter = new FilterCard("cards with mana value 3 or greater from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 2));
    }

    public OracleOfTragedy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // When Oracle of Tragedy enters the battlefield or dies, choose one --
        // * Draw a card, then discard a card.
        Ability ability = new EntersBattlefieldOrDiesSourceTriggeredAbility(
                new DrawDiscardControllerEffect(1, 1), false
        );

        // * Shuffle up to four target cards with mana value 3 or greater from your graveyard into your library.
        ability.addMode(new Mode(new ShuffleIntoLibraryTargetEffect().setText(
                "shuffle up to four target cards with mana value 3 or greater from your graveyard into your library"
        )).addTarget(new TargetCardInYourGraveyard(0, 4, filter)));
        this.addAbility(ability);
    }

    private OracleOfTragedy(final OracleOfTragedy card) {
        super(card);
    }

    @Override
    public OracleOfTragedy copy() {
        return new OracleOfTragedy(this);
    }
}
