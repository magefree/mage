
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ruleModifying.CastOnlyIfYouHaveCastAnotherSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class HewedStoneRetainers extends CardImpl {

    public HewedStoneRetainers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Cast Hewed Stone Retainers only if you've cast another spell this turn.
       this.addAbility(new SimpleStaticAbility(Zone.ALL, new CastOnlyIfYouHaveCastAnotherSpellEffect()));
    }

    private HewedStoneRetainers(final HewedStoneRetainers card) {
        super(card);
    }

    @Override
    public HewedStoneRetainers copy() {
        return new HewedStoneRetainers(this);
    }
}

