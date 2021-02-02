
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Loki
 */
public final class BoggartBirthRite extends CardImpl {

    private static final FilterCard filter = new FilterCard("Goblin card from your graveyard");

    static {
        filter.add(SubType.GOBLIN.getPredicate());
    }

    public BoggartBirthRite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.TRIBAL,CardType.SORCERY},"{B}");
        this.subtype.add(SubType.GOBLIN);

        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));
    }

    private BoggartBirthRite(final BoggartBirthRite card) {
        super(card);
    }

    @Override
    public BoggartBirthRite copy() {
        return new BoggartBirthRite(this);
    }
}
