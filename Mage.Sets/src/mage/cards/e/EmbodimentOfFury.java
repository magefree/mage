
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public final class EmbodimentOfFury extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("land creatures");

    static {
        filter.add(CardType.LAND.getPredicate());
    }

    public EmbodimentOfFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Land creatures you control have trample.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield, filter)));

        // Landfall -- Whenever a land you control enters, you may have target land you control
        // become a 3/3 Elemental creature with haste until end of turn. It's still a land.
        Ability ability = new LandfallAbility(new BecomesCreatureTargetEffect(
            new CreatureToken(
                3, 3,
                "3/3 Elemental creature with haste",
                SubType.ELEMENTAL
            ).withAbility(HasteAbility.getInstance()),
            false, true, Duration.EndOfTurn
        ), true);
        ability.addTarget(new TargetPermanent(new FilterControlledLandPermanent()));
        this.addAbility(ability);
    }

    private EmbodimentOfFury(final EmbodimentOfFury card) {
        super(card);
    }

    @Override
    public EmbodimentOfFury copy() {
        return new EmbodimentOfFury(this);
    }
}
