package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderAllEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.ModifiedPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GuardiansOfOboro extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("modified creatures you control");

    static {
        filter.add(ModifiedPredicate.instance);
    }

    public GuardiansOfOboro(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.MOONFOLK);
        this.subtype.add(SubType.SAMURAI);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Modified creatures you control can attack as though they didn't have defender.
        this.addAbility(new SimpleStaticAbility(
                new CanAttackAsThoughItDidntHaveDefenderAllEffect(Duration.WhileOnBattlefield, filter)
        ));
    }

    private GuardiansOfOboro(final GuardiansOfOboro card) {
        super(card);
    }

    @Override
    public GuardiansOfOboro copy() {
        return new GuardiansOfOboro(this);
    }
}
