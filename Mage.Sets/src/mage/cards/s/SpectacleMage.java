package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpectacleMage extends CardImpl {

    private static final FilterCard filter = new FilterInstantOrSorceryCard();

    static {
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 4));
    }

    public SpectacleMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Instant and sorcery spells you cast with mana value 5 or greater cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)
                .setText("instant and sorcery spells you cast with mana value 5 or greater cost {1} less to cast")));
    }

    private SpectacleMage(final SpectacleMage card) {
        super(card);
    }

    @Override
    public SpectacleMage copy() {
        return new SpectacleMage(this);
    }
}
