

package mage.cards.i;

import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class IsolatedChapel extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent();

    static {
        filter.add(Predicates.or(new SubtypePredicate(SubType.PLAINS), new SubtypePredicate(SubType.SWAMP)));
    }

    public IsolatedChapel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},null);

        Condition controls = new InvertCondition(new PermanentsOnTheBattlefieldCondition(filter));
        String abilityText = " tapped unless you control a Plains or a Swamp";
        this.addAbility(new EntersBattlefieldAbility(new ConditionalOneShotEffect(new TapSourceEffect(), controls, abilityText), abilityText));
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlackManaAbility());
    }

    public IsolatedChapel(final IsolatedChapel card) {
        super(card);
    }

    @Override
    public IsolatedChapel copy() {
        return new IsolatedChapel(this);
    }
}
