package mage.cards.a;

import mage.MageInt;
import mage.abilities.abilityword.ConstellationAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.PegasusToken2;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArchonOfSunsGrace extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent(SubType.PEGASUS, "Pegasus creatures");

    public ArchonOfSunsGrace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        this.subtype.add(SubType.ARCHON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Pegasus creatures you control have lifelink.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                LifelinkAbility.getInstance(), Duration.WhileOnBattlefield, filter
        )));

        // Constellation—Whenever an enchantment enters the battlefield under your control, create a 2/2 white Pegasus creature token with flying.
        this.addAbility(new ConstellationAbility(
                new CreateTokenEffect(new PegasusToken2()), false, false
        ));
    }

    private ArchonOfSunsGrace(final ArchonOfSunsGrace card) {
        super(card);
    }

    @Override
    public ArchonOfSunsGrace copy() {
        return new ArchonOfSunsGrace(this);
    }
}
