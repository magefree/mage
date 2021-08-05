package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.CompletedDungeonTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DungeonCrawler extends CardImpl {

    public DungeonCrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Dungeon Crawler enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Whenever you complete a dungeon, you may return Dungeon Crawler from your graveyard to your hand.
        this.addAbility(new CompletedDungeonTriggeredAbility(
                Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(), true
        ));
    }

    private DungeonCrawler(final DungeonCrawler card) {
        super(card);
    }

    @Override
    public DungeonCrawler copy() {
        return new DungeonCrawler(this);
    }
}
