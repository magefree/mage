package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.target.common.TargetNonlandPermanent;
import mage.target.targetadjustment.TargetAdjuster;

/**
 *
 * @author LevelX2
 */
public final class Repeal extends CardImpl {

    public Repeal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{U}");

        // Return target nonland permanent with converted mana cost X to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(new FilterNonlandPermanent("nonland permanent with mana value X")));
        this.getSpellAbility().setTargetAdjuster(RepealAdjuster.instance);

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private Repeal(final Repeal card) {
        super(card);
    }

    @Override
    public Repeal copy() {
        return new Repeal(this);
    }
}

enum RepealAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        int xValue = ability.getManaCostsToPay().getX();
        FilterNonlandPermanent filter = new FilterNonlandPermanent("nonland permanent with mana value " + xValue);
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, xValue));
        ability.addTarget(new TargetNonlandPermanent(filter));
    }
}
