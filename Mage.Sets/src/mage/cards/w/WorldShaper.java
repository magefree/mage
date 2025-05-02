package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.ReturnFromYourGraveyardToBattlefieldAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class WorldShaper extends CardImpl {

    public WorldShaper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever World Shaper attacks, you may put the top three cards of your library into your graveyard.
        this.addAbility(new AttacksTriggeredAbility(new MillCardsControllerEffect(3), true));

        // When World Shaper dies, return all land cards from your graveyard to the battlefield tapped.
        this.addAbility(new DiesSourceTriggeredAbility(new ReturnFromYourGraveyardToBattlefieldAllEffect(
                StaticFilters.FILTER_CARD_LANDS, true)));
    }

    private WorldShaper(final WorldShaper card) {
        super(card);
    }

    @Override
    public WorldShaper copy() {
        return new WorldShaper(this);
    }
}
