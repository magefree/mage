
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedByCreatureTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterArtifactCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class OgreLeadfoot extends CardImpl {

    public OgreLeadfoot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");
        
        this.subtype.add(SubType.OGRE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Ogre Leadfoot becomes blocked by an artifact creature, destroy that creature.
        this.addAbility(new BecomesBlockedByCreatureTriggeredAbility(new DestroyTargetEffect(), new FilterArtifactCreaturePermanent("an artifact creature"), false));
    }

    private OgreLeadfoot(final OgreLeadfoot card) {
        super(card);
    }

    @Override
    public OgreLeadfoot copy() {
        return new OgreLeadfoot(this);
    }
}
