package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShrineSteward extends CardImpl {

    private static final FilterCard filter = new FilterCard("an Aura or Shrine card");

    static {
        filter.add(Predicates.or(
                SubType.AURA.getPredicate(),
                SubType.SHRINE.getPredicate()
        ));
    }

    public ShrineSteward(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Shrine Steward enters the battlefield, you may search your library for an Aura or Shrine card, reveal it, put it into your hand, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(filter), true
        ), true));
    }

    private ShrineSteward(final ShrineSteward card) {
        super(card);
    }

    @Override
    public ShrineSteward copy() {
        return new ShrineSteward(this);
    }
}
