package mage.cards.d;

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
public final class DeathcapMarionette extends CardImpl {

    public DeathcapMarionette(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.FUNGUS);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // When Deathcap Marionette enters the battlefield, you may mill two cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(2), true));
    }

    private DeathcapMarionette(final DeathcapMarionette card) {
        super(card);
    }

    @Override
    public DeathcapMarionette copy() {
        return new DeathcapMarionette(this);
    }
}
