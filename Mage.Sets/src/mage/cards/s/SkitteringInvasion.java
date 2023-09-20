

package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.EldraziSpawnToken;

/**
 *
 * @author Loki
 */
public final class SkitteringInvasion extends CardImpl {

    public SkitteringInvasion (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.TRIBAL,CardType.SORCERY},"{7}");
        this.subtype.add(SubType.ELDRAZI);
        this.getSpellAbility().addEffect(new CreateTokenEffect(new EldraziSpawnToken(), 5));
    }

    private SkitteringInvasion(final SkitteringInvasion card) {
        super(card);
    }

    @Override
    public SkitteringInvasion copy() {
        return new SkitteringInvasion(this);
    }

}
