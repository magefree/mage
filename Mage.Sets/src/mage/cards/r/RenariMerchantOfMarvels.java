package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.ChooseABackgroundAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RenariMerchantOfMarvels extends CardImpl {

    private static final FilterCard filter = new FilterCard("Dragon spells and artifact spells");

    static {
        filter.add(Predicates.or(
                SubType.DRAGON.getPredicate(),
                CardType.ARTIFACT.getPredicate()
        ));
    }

    public RenariMerchantOfMarvels(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // You may cast Dragon spells and artifact spells as though they had flash.
        this.addAbility(new SimpleStaticAbility(
                new CastAsThoughItHadFlashAllEffect(Duration.WhileOnBattlefield, filter)
        ));

        // Choose a Background
        this.addAbility(ChooseABackgroundAbility.getInstance());
    }

    private RenariMerchantOfMarvels(final RenariMerchantOfMarvels card) {
        super(card);
    }

    @Override
    public RenariMerchantOfMarvels copy() {
        return new RenariMerchantOfMarvels(this);
    }
}
