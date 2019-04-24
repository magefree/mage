
package mage.cards.d;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.AnotherPredicate;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public final class DarkslickShores extends CardImpl {

    private final static FilterLandPermanent filter = new FilterLandPermanent();

    static {
        filter.add(new AnotherPredicate());
    }

    public DarkslickShores(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, null);

        // Darkslick Shores enters the battlefield tapped unless you control two or fewer other lands.
        Condition controls = new InvertCondition(new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.FEWER_THAN, 3));
        String abilityText = " tapped unless you control two or fewer other lands";
        this.addAbility(new EntersBattlefieldAbility(new ConditionalOneShotEffect(new TapSourceEffect(), controls, abilityText), abilityText));

        this.addAbility(new BlueManaAbility());
        this.addAbility(new BlackManaAbility());
    }

    public DarkslickShores(final DarkslickShores card) {
        super(card);
    }

    @Override
    public DarkslickShores copy() {
        return new DarkslickShores(this);
    }

}
