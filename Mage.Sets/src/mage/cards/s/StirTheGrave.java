
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class StirTheGrave extends CardImpl {

    public StirTheGrave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{B}");


        // Return target creature card with converted mana cost X or less from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(new FilterCreatureCard("creature card with converted mana cost X or less from your graveyard")));
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        int xValue = ability.getManaCostsToPay().getX();
        FilterCard filter = new FilterCreatureCard("creature card with converted mana cost " + xValue +  " or less from your graveyard");
        filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, xValue + 1));
        ability.getTargets().add(new TargetCardInYourGraveyard(filter));
    }

    public StirTheGrave(final StirTheGrave card) {
        super(card);
    }

    @Override
    public StirTheGrave copy() {
        return new StirTheGrave(this);
    }
}
