
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.effects.common.combat.CantBlockCreaturesSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;

/**
 * @author noxx
 */
public final class HuntedGhoul extends CardImpl {

    public HuntedGhoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Hunted Ghoul can't block Humans.
        this.addAbility(new SimpleEvasionAbility(new CantBlockCreaturesSourceEffect(new FilterCreaturePermanent(SubType.HUMAN, "Humans"))));
    }

    private HuntedGhoul(final HuntedGhoul card) {
        super(card);
    }

    @Override
    public HuntedGhoul copy() {
        return new HuntedGhoul(this);
    }
}
