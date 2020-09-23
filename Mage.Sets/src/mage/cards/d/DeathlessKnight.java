package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.GainLifeFirstTimeTriggeredAbility;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DeathlessKnight extends CardImpl {

    public DeathlessKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B/G}{B/G}{B/G}{B/G}");

        this.subtype.add(SubType.SKELETON);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When you gain life for the first time each turn, return Deathless Knight from your graveyard to your hand.
        this.addAbility(new GainLifeFirstTimeTriggeredAbility(
                Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(), false, true
        ));
    }

    private DeathlessKnight(final DeathlessKnight card) {
        super(card);
    }

    @Override
    public DeathlessKnight copy() {
        return new DeathlessKnight(this);
    }
}
