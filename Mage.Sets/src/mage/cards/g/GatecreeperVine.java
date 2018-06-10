
package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class GatecreeperVine extends CardImpl {

    private static final FilterCard filter = new FilterCard("basic land card or a Gate card");
    static {
        filter.add(
            Predicates.or(
                Predicates.and(
                         new CardTypePredicate(CardType.LAND),
                         new SupertypePredicate(SuperType.BASIC)),
                new SubtypePredicate(SubType.GATE)));
    }

    public GatecreeperVine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.PLANT);

        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // When Gatecreeper Vine enters the battlefield, you may search your library for a basic land card or a Gate card, reveal it, put it into your hand, then shuffle your library.
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(target, true, true), true));
    }

    public GatecreeperVine(final GatecreeperVine card) {
        super(card);
    }

    @Override
    public GatecreeperVine copy() {
        return new GatecreeperVine(this);
    }
}
