

package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 * @author Loki
 */
public final class Earthshaker extends CardImpl {
    private static final FilterCreaturePermanent creatureFilter = new FilterCreaturePermanent("creature without flying");

    static {
        creatureFilter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public Earthshaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}{R}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);
        // Whenever you cast a Spirit or Arcane spell, Earthshaker deals 2 damage to each creature without flying.
        this.addAbility(new SpellCastControllerTriggeredAbility(new DamageAllEffect(StaticValue.get(2) , creatureFilter), StaticFilters.FILTER_SPIRIT_OR_ARCANE_CARD, false));
    }

    private Earthshaker(final Earthshaker card) {
        super(card);
    }

    @Override
    public Earthshaker copy() {
        return new Earthshaker(this);
    }

}
