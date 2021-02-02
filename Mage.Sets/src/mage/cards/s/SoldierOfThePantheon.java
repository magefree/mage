
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterObject;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.MulticoloredPredicate;

/**
 *
 * @author LevelX2
 */
public final class SoldierOfThePantheon extends CardImpl {

    private static final FilterObject filter = new FilterObject("multicolored");

    static {
        filter.add(MulticoloredPredicate.instance);
    }

    public SoldierOfThePantheon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Protection from multicolored
        this.addAbility(new ProtectionAbility(filter));
        // Whenever an opponent casts a multicolored spell, you gain 1 life.
        this.addAbility(new SpellCastOpponentTriggeredAbility(Zone.BATTLEFIELD, new GainLifeEffect(1), StaticFilters.FILTER_SPELL_A_MULTICOLORED, false));

    }

    private SoldierOfThePantheon(final SoldierOfThePantheon card) {
        super(card);
    }

    @Override
    public SoldierOfThePantheon copy() {
        return new SoldierOfThePantheon(this);
    }
}
