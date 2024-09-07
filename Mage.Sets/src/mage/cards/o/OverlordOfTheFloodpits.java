package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ImpendingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OverlordOfTheFloodpits extends CardImpl {

    public OverlordOfTheFloodpits(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Impending 4--{1}{U}{U}
        this.addAbility(new ImpendingAbility("{1}{U}{U}"));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Overlord of the Floodpits enters or attacks, draw two cards, then discard a card.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new DrawDiscardControllerEffect(2, 1)));
    }

    private OverlordOfTheFloodpits(final OverlordOfTheFloodpits card) {
        super(card);
    }

    @Override
    public OverlordOfTheFloodpits copy() {
        return new OverlordOfTheFloodpits(this);
    }
}
