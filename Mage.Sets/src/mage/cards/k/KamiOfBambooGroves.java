package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ConjureCardEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.keyword.ChannelAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Svyatoslav28
 */
public final class KamiOfBambooGroves extends CardImpl {

    public KamiOfBambooGroves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Kami of Bamboo Groves enters the battlefield, you may put a land card from your hand onto the battlefield tapped.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new PutCardFromHandOntoBattlefieldEffect(
                StaticFilters.FILTER_CARD_LAND_A, false, true
        ), false));
        // Channel -- {2}{G}, Discard Kami of Bamboo Groves: Conjure two cards named Forest into your hand.
        this.addAbility(new ChannelAbility("{2}{G}", new ConjureCardEffect("Forest", Zone.HAND, 2)));
    }

    private KamiOfBambooGroves(final KamiOfBambooGroves card) {
        super(card);
    }

    @Override
    public KamiOfBambooGroves copy() {
        return new KamiOfBambooGroves(this);
    }
}
