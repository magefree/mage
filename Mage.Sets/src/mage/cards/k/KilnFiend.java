
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author North
 */
public final class KilnFiend extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an instant or sorcery spell");
    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));
    }

    public KilnFiend(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        this.addAbility(new SpellCastControllerTriggeredAbility(new BoostSourceEffect(3, 0, Duration.EndOfTurn), filter, false));
    }

    public KilnFiend(final KilnFiend card) {
        super(card);
    }

    @Override
    public KilnFiend copy() {
        return new KilnFiend(this);
    }
}
