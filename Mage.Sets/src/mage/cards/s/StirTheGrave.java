
package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class StirTheGrave extends CardImpl {

    public StirTheGrave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B}");

        // Return target creature card with converted mana cost X or less from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect().setText("return target creature card with mana value X or less from your graveyard to the battlefield"));
        this.getSpellAbility().setTargetAdjuster(StirTheGraveAdjuster.instance);
    }

    private StirTheGrave(final StirTheGrave card) {
        super(card);
    }

    @Override
    public StirTheGrave copy() {
        return new StirTheGrave(this);
    }
}

enum StirTheGraveAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        int xValue = ability.getManaCostsToPay().getX();
        FilterCard filter = new FilterCreatureCard("creature card with mana value " + xValue + " or less from your graveyard");
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, xValue + 1));
        ability.getTargets().add(new TargetCardInYourGraveyard(filter));
    }
}