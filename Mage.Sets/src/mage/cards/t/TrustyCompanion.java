
package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantAttackAloneSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class TrustyCompanion extends CardImpl {

    public TrustyCompanion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.HYENA);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // Trusty Companion can't attack alone.
        this.addAbility(new SimpleStaticAbility(new CantAttackAloneSourceEffect()));
    }
    
    private TrustyCompanion(final TrustyCompanion card) {
        super(card);
    }

    @Override
    public TrustyCompanion copy() {
        return new TrustyCompanion(this);
    }
}
