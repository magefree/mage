package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GreatDivideGuide extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("each land and Ally");

    static {
        filter.add(Predicates.or(
                CardType.LAND.getPredicate(),
                SubType.ALLY.getPredicate()
        ));
    }

    public GreatDivideGuide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Each land and Ally you control has "{T} : Add one mana of any color."
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new AnyColorManaAbility(), Duration.WhileOnBattlefield, filter
        )));
    }

    private GreatDivideGuide(final GreatDivideGuide card) {
        super(card);
    }

    @Override
    public GreatDivideGuide copy() {
        return new GreatDivideGuide(this);
    }
}
