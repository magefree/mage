
package mage.cards.l;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author Backfir3
 */
public final class LayWaste extends CardImpl {

    public LayWaste(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R}");


        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetLandPermanent());

        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private LayWaste(final LayWaste card) {
        super(card);
    }

    @Override
    public LayWaste copy() {
        return new LayWaste(this);
    }
}
