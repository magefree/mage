package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VenomousHierophant extends CardImpl {

    public VenomousHierophant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.GORGON);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // When Venomous Hierophant enters the battlefield, put the top three cards of your library into your graveyard.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(3)));
    }

    private VenomousHierophant(final VenomousHierophant card) {
        super(card);
    }

    @Override
    public VenomousHierophant copy() {
        return new VenomousHierophant(this);
    }
}
