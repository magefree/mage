package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CoppercoatVanguard extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.HUMAN, "each other Human");
    private static final FilterPermanent filter2 = new FilterPermanent(SubType.HUMAN, "Human");

    public CoppercoatVanguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Each other Human you control gets +1/+0 and has ward {1}.
        Ability ability = new SimpleStaticAbility(new BoostControlledEffect(
                1, 0, Duration.WhileOnBattlefield, filter, true
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                new WardAbility(new GenericManaCost(1), false),
                Duration.WhileOnBattlefield, filter2, true
        ).setText("and has ward {1}. <i>(Whenever it becomes the target " +
                "of a spell or ability an opponent controls, counter it unless that player pays {1}.)</i>"));
        this.addAbility(ability);
    }

    private CoppercoatVanguard(final CoppercoatVanguard card) {
        super(card);
    }

    @Override
    public CoppercoatVanguard copy() {
        return new CoppercoatVanguard(this);
    }
}
