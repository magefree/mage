package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.EquipmentAttachedCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.permanent.token.SoldierToken;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author muz
 */
public final class CaptainAmericaLiberator extends CardImpl {

    private static final FilterCard filter = new FilterCard("an Equipment card with mana value 3 or less");

    static {
        filter.add(SubType.EQUIPMENT.getPredicate());
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 3));
    }

    public CaptainAmericaLiberator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // When Captain America enters, you may search your library for an Equipment card with mana value 3 or less, put it onto the battlefield, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInPlayEffect(
            new TargetCardInLibrary(filter), false, false, true
        )));

        // Whenever Captain America attacks, for each Equipment attached to him, create a 1/1 white Soldier creature token.
        Ability ability = new AttacksTriggeredAbility(new CreateTokenEffect(
            new SoldierToken(), new EquipmentAttachedCount()
        ).setText("for each Equipment attached to him, create a 1/1 white Soldier creature token"));
        this.addAbility(ability);
    }

    private CaptainAmericaLiberator(final CaptainAmericaLiberator card) {
        super(card);
    }

    @Override
    public CaptainAmericaLiberator copy() {
        return new CaptainAmericaLiberator(this);
    }
}
