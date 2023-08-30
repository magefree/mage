package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.CascadeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ImotiCelebrantOfBounty extends CardImpl {

    private static final FilterCard filter = new FilterCard("spells you cast with mana value 6 or greater");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 5));
    }

    public ImotiCelebrantOfBounty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.NAGA);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Cascade
        this.addAbility(new CascadeAbility());

        // Spells you cast with converted mana cost 6 or greater have cascade.
        this.addAbility(new SimpleStaticAbility(
                new GainAbilityControlledSpellsEffect(new CascadeAbility(false), filter)
        ));
    }

    private ImotiCelebrantOfBounty(final ImotiCelebrantOfBounty card) {
        super(card);
    }

    @Override
    public ImotiCelebrantOfBounty copy() {
        return new ImotiCelebrantOfBounty(this);
    }
}
