package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterBySubtypeCard;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AlpineGuide extends CardImpl {

    private static final FilterCard filter = new FilterBySubtypeCard(SubType.MOUNTAIN);
    private static final FilterPermanent filter2 = new FilterControlledPermanent(SubType.MOUNTAIN, "Mountain");

    public AlpineGuide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.SNOW);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Alpine Guide enters the battlefield, you may search your library for a Mountain card, put that card onto the battlefield tapped, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(filter), true, true
        ), true));

        // Alpine Guide attacks each combat if able.
        this.addAbility(new AttacksEachCombatStaticAbility());

        // When Alpine Guide leaves the battlefield, sacrifice a Mountain.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(
                new SacrificeControllerEffect(filter2, 1, null), false
        ));
    }

    private AlpineGuide(final AlpineGuide card) {
        super(card);
    }

    @Override
    public AlpineGuide copy() {
        return new AlpineGuide(this);
    }
}
