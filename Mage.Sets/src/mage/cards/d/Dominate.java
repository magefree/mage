
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class Dominate extends CardImpl {

    public Dominate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{1}{U}{U}");

        // Gain control of target creature with converted mana cost X or less.
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.Custom, true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(new FilterCreaturePermanent("creature with converted mana cost X or less")));
    }

    public Dominate(final Dominate card) {
        super(card);
    }
    
    @Override
    public void adjustTargets(Ability ability, Game game) {
        if(ability instanceof SpellAbility) {
            ability.getTargets().clear();
            int xValue = ability.getManaCostsToPay().getX();
            FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with converted mana cost X or less");
            filter.add(Predicates.not(new ConvertedManaCostPredicate(ComparisonType.MORE_THAN, xValue)));
            ability.addTarget(new TargetCreaturePermanent(filter));
        }
    }

    @Override
    public Dominate copy() {
        return new Dominate(this);
    }
}
