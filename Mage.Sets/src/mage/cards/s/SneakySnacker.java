package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SneakySnacker extends CardImpl {

    public SneakySnacker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}");

        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When you draw your third card in a turn, return Sneaky Snacker from your graveyard to the battlefield tapped.
        this.addAbility(new DrawNthCardTriggeredAbility(
                Zone.GRAVEYARD,
                new ReturnSourceFromGraveyardToBattlefieldEffect(true, false),
                false,
                TargetController.YOU,
                3
        ).setTriggerPhrase("When you draw your third card in a turn, "));
    }

    private SneakySnacker(final SneakySnacker card) {
        super(card);
    }

    @Override
    public SneakySnacker copy() {
        return new SneakySnacker(this);
    }
}
