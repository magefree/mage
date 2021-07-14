package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.RollDieWithResultTableEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HoardingOgre extends CardImpl {

    public HoardingOgre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.OGRE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Hoarding Ogre attacks, roll a d20.
        RollDieWithResultTableEffect effect = new RollDieWithResultTableEffect();
        this.addAbility(new AttacksTriggeredAbility(effect, false));

        // 1-9 | Create a Treasure token.
        effect.addTableEntry(1, 9, new CreateTokenEffect(new TreasureToken()));

        // 10-19 | Create two Treasure tokens.
        effect.addTableEntry(10, 19, new CreateTokenEffect(new TreasureToken(), 2));
        
        // 20 | Create three Treasure tokens.
        effect.addTableEntry(20, 20, new CreateTokenEffect(new TreasureToken(), 3));
    }

    private HoardingOgre(final HoardingOgre card) {
        super(card);
    }

    @Override
    public HoardingOgre copy() {
        return new HoardingOgre(this);
    }
}
