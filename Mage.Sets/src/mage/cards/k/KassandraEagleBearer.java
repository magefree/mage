package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.search.SearchLibraryGraveyardHandPutOntoBattlefieldEffect;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.LegendaryEquippedPredicate;

/**
 * @author grimreap124
 */
public final class KassandraEagleBearer extends CardImpl {

    private static final FilterCard filter = new FilterCard("card named The Spear of Leonidas");
    private static final FilterControlledCreaturePermanent equipFilter
            = new FilterControlledCreaturePermanent("creature you control with a legendary Equipment attached to it");
    static {
        filter.add(new NamePredicate("The Spear of Leonidas"));
        equipFilter.add(LegendaryEquippedPredicate.instance);
    }

    public KassandraEagleBearer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Kassandra enters the battlefield, search your graveyard, hand, and library for a card named The Spear of Leonidas, put it onto the battlefield, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryGraveyardHandPutOntoBattlefieldEffect(filter), false));
        // Whenever a creature you control with a legendary Equipment attached to it deals combat damage to a player, draw a card.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(new DrawCardSourceControllerEffect(1), equipFilter, false, SetTargetPointer.NONE, true));
    }

    private KassandraEagleBearer(final KassandraEagleBearer card) {
        super(card);
    }

    @Override
    public KassandraEagleBearer copy() {
        return new KassandraEagleBearer(this);
    }
}
