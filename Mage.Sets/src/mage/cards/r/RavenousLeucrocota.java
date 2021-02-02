
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.MonstrosityAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class RavenousLeucrocota extends CardImpl {

    public RavenousLeucrocota(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // {6}{G}: Monstrosity 3.
        this.addAbility(new MonstrosityAbility("{6}{G}", 3));
    }

    private RavenousLeucrocota(final RavenousLeucrocota card) {
        super(card);
    }

    @Override
    public RavenousLeucrocota copy() {
        return new RavenousLeucrocota(this);
    }
}
