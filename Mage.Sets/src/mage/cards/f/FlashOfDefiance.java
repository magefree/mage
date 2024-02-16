
package mage.cards.f;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBlockAllEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TimingRule;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author fireshoes
 */
public final class FlashOfDefiance extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("green creatures and white creatures");

    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.WHITE),
                new ColorPredicate(ObjectColor.GREEN)));
    }

    public FlashOfDefiance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}");

        // Green creatures and white creatures can't block this turn.
        this.getSpellAbility().addEffect(new CantBlockAllEffect(filter, Duration.EndOfTurn));
        
        // Flashback-{1}{R}, Pay 3 life.
        Ability ability = new FlashbackAbility(this, new ManaCostsImpl<>("{1}{R}"));
        ability.addCost(new PayLifeCost(3));
        this.addAbility(ability);
    }

    private FlashOfDefiance(final FlashOfDefiance card) {
        super(card);
    }

    @Override
    public FlashOfDefiance copy() {
        return new FlashOfDefiance(this);
    }
}
