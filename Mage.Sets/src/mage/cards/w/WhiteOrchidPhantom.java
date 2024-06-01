package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayTargetControllerEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetNonBasicLandPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class WhiteOrchidPhantom extends CardImpl {

    public WhiteOrchidPhantom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // When White Orchid Phantom enters the battlefield, destroy up to one target nonbasic land. Its controller may search their library for a basic land card, put it onto the battlefield tapped, then shuffle.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetNonBasicLandPermanent(0, 1));
        ability.addEffect(new SearchLibraryPutInPlayTargetControllerEffect(true));
        this.addAbility(ability);
    }

    private WhiteOrchidPhantom(final WhiteOrchidPhantom card) {
        super(card);
    }

    @Override
    public WhiteOrchidPhantom copy() {
        return new WhiteOrchidPhantom(this);
    }
}
