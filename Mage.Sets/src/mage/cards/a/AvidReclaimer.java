
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author spjspj
 */
public final class AvidReclaimer extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(new CardTypePredicate(CardType.PLANESWALKER));
        filter.add(new SubtypePredicate(SubType.NISSA));
    }

    public AvidReclaimer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {T}: Add {G} or {U}. If you control a Nissa planeswalker, you gain 2 life.
        Ability GreenManaAbility = new GreenManaAbility();
        GreenManaAbility.addEffect(new ConditionalOneShotEffect(new GainLifeEffect(2), new PermanentsOnTheBattlefieldCondition(filter), "If you control a Nissa planeswalker, you gain 2 life."));
        this.addAbility(GreenManaAbility);

        Ability BlueManaAbility = new BlueManaAbility();
        BlueManaAbility.addEffect(new ConditionalOneShotEffect(new GainLifeEffect(2), new PermanentsOnTheBattlefieldCondition(filter), "If you control a Nissa planeswalker, you gain 2 life."));
        this.addAbility(BlueManaAbility);
    }

    public AvidReclaimer(final AvidReclaimer card) {
        super(card);
    }

    @Override
    public AvidReclaimer copy() {
        return new AvidReclaimer(this);
    }
}
