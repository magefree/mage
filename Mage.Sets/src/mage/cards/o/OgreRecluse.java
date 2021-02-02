
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.common.TapSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class OgreRecluse extends CardImpl {

    public OgreRecluse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Whenever a player casts a spell, tap Ogre Recluse.
        this.addAbility(new SpellCastAllTriggeredAbility(new TapSourceEffect(), false));
    }

    private OgreRecluse(final OgreRecluse card) {
        super(card);
    }

    @Override
    public OgreRecluse copy() {
        return new OgreRecluse(this);
    }
}
