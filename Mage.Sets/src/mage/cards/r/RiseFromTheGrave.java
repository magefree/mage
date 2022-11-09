
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.AddCreatureTypeAdditionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class RiseFromTheGrave extends CardImpl {

    public RiseFromTheGrave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{B}");


        // Put target creature card from a graveyard onto the battlefield under your control. That creature is a black Zombie in addition to its other colors and types.
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(new FilterCreatureCard("creature card from a graveyard")));
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addEffect(new AddCreatureTypeAdditionEffect(SubType.ZOMBIE, true));
    }

    private RiseFromTheGrave(final RiseFromTheGrave card) {
        super(card);
    }

    @Override
    public RiseFromTheGrave copy() {
        return new RiseFromTheGrave(this);
    }
}
