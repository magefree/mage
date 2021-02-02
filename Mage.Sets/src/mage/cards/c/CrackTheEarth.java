
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author LevelX
 */
public final class CrackTheEarth extends CardImpl {

    public CrackTheEarth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{R}");
        this.subtype.add(SubType.ARCANE);

        // Each player sacrifices a permanent.
        this.getSpellAbility().addEffect(new SacrificeAllEffect(1, new FilterControlledPermanent("permanent")));

    }

    private CrackTheEarth(final CrackTheEarth card) {
        super(card);
    }

    @Override
    public CrackTheEarth copy() {
        return new CrackTheEarth(this);
    }
}

