package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SarulfsPackmate extends CardImpl {

    public SarulfsPackmate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Sarulf's Packmate enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));

        // Foretell {1}{G}
        this.addAbility(new ForetellAbility(this, "{1}{G}"));
    }

    private SarulfsPackmate(final SarulfsPackmate card) {
        super(card);
    }

    @Override
    public SarulfsPackmate copy() {
        return new SarulfsPackmate(this);
    }
}
