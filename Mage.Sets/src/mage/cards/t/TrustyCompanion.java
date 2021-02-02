
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.CantAttackAloneAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class TrustyCompanion extends CardImpl {

    public TrustyCompanion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HYENA);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // Trusty Companion can't attack alone.
        this.addAbility(new CantAttackAloneAbility());
    }
    
    private TrustyCompanion(final TrustyCompanion card) {
        super(card);
    }

    @Override
    public TrustyCompanion copy() {
        return new TrustyCompanion(this);
    }
}
