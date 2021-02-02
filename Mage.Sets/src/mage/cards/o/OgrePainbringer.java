package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class OgrePainbringer extends CardImpl {

    public OgrePainbringer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        this.subtype.add(SubType.OGRE);

        this.power = new MageInt(7);
        this.toughness = new MageInt(3);

        // When Ogre Painbringer enters the battlefield, it deals 3 damage to each player.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DamagePlayersEffect(3, TargetController.ANY, "it")));
    }

    private OgrePainbringer(final OgrePainbringer card) {
        super(card);
    }

    @Override
    public OgrePainbringer copy() {
        return new OgrePainbringer(this);
    }
}
