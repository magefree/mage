
package mage.cards.q;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.PayCostToAttackBlockEffectImpl;
import mage.abilities.effects.common.combat.CantAttackBlockUnlessPaysSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class QalSismaBehemoth extends CardImpl {

    public QalSismaBehemoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Qal Sisma Behemoth can't attack or block unless you pay {2}.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackBlockUnlessPaysSourceEffect(new ManaCostsImpl<>("{2}"), PayCostToAttackBlockEffectImpl.RestrictType.ATTACK_AND_BLOCK)));

    }

    private QalSismaBehemoth(final QalSismaBehemoth card) {
        super(card);
    }

    @Override
    public QalSismaBehemoth copy() {
        return new QalSismaBehemoth(this);
    }
}
