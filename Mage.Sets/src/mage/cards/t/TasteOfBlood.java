
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author Loki
 */
public final class TasteOfBlood extends CardImpl {

    public TasteOfBlood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        this.getSpellAbility().addEffect(new DamageTargetEffect(1));
        this.getSpellAbility().addEffect(new GainLifeEffect(1).concatBy("and"));
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker());
    }

    private TasteOfBlood(final TasteOfBlood card) {
        super(card);
    }

    @Override
    public TasteOfBlood copy() {
        return new TasteOfBlood(this);
    }

}
