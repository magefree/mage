

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CantBeCounteredControlledEffect;
import mage.abilities.effects.common.CantBeCounteredSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.PowerPredicate;


/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class SpellbreakerBehemoth extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("Creature spells you control with power 5 or greater");

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 4));
    }

    public SpellbreakerBehemoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{G}{G}");


        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        this.addAbility(new SimpleStaticAbility(Zone.STACK, new CantBeCounteredSourceEffect()));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeCounteredControlledEffect(filter, Duration.WhileOnBattlefield)));
    }

    private SpellbreakerBehemoth(final SpellbreakerBehemoth card) {
        super(card);
    }

    @Override
    public SpellbreakerBehemoth copy() {
        return new SpellbreakerBehemoth(this);
    }

}
