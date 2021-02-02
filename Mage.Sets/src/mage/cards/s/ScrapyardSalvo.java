
package mage.cards.s;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterArtifactCard;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author North
 */
public final class ScrapyardSalvo extends CardImpl {

    public ScrapyardSalvo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}{R}");

        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker());
        this.getSpellAbility().addEffect(new DamageTargetEffect(new CardsInControllerGraveyardCount(new FilterArtifactCard())));
    }

    private ScrapyardSalvo(final ScrapyardSalvo card) {
        super(card);
    }

    @Override
    public ScrapyardSalvo copy() {
        return new ScrapyardSalvo(this);
    }
}
