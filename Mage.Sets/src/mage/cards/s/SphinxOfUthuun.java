package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.RevealAndSeparatePilesEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author North
 */
public final class SphinxOfUthuun extends CardImpl {

    public SphinxOfUthuun(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");
        this.subtype.add(SubType.SPHINX);

        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new EntersBattlefieldTriggeredAbility(new RevealAndSeparatePilesEffect(
                5, TargetController.OPPONENT, TargetController.YOU, Zone.GRAVEYARD
        )));
    }

    private SphinxOfUthuun(final SphinxOfUthuun card) {
        super(card);
    }

    @Override
    public SphinxOfUthuun copy() {
        return new SphinxOfUthuun(this);
    }
}
