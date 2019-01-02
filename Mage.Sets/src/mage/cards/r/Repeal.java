
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author LevelX2
 */
public final class Repeal extends CardImpl {

    public Repeal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{U}");


        // Return target nonland permanent with converted mana cost X to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(new FilterNonlandPermanent("nonland permanent with converted mana cost X")));


        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            ability.getTargets().clear();
            int xValue = ability.getManaCostsToPay().getX();
            FilterNonlandPermanent filter = new FilterNonlandPermanent("nonland permanent with converted mana cost " + xValue);
            filter.add(new ConvertedManaCostPredicate(ComparisonType.EQUAL_TO, xValue));
            ability.addTarget(new TargetNonlandPermanent(filter));
        }
    }

    public Repeal(final Repeal card) {
        super(card);
    }

    @Override
    public Repeal copy() {
        return new Repeal(this);
    }
}
