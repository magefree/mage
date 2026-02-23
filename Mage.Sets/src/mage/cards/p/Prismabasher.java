package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetadjustment.TargetsCountAdjuster;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.ColorsAmongControlledPermanentsCount;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class Prismabasher extends CardImpl {

    public Prismabasher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Vivid -- When this creature enters, up to X target creatures you control get +X/+X until end of turn, where X is the number of colors among permanents you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(
            new BoostTargetEffect(
                ColorsAmongControlledPermanentsCount.ALL_PERMANENTS,
                ColorsAmongControlledPermanentsCount.ALL_PERMANENTS
            ).setText("up to X target creatures you control get +X/+X until end of turn, " +
                "where X is the number of colors among permanents you control")
        );
        ability.addTarget(new TargetControlledCreaturePermanent(0, 0));
        ability.setTargetAdjuster(new TargetsCountAdjuster(ColorsAmongControlledPermanentsCount.ALL_PERMANENTS));
        this.addAbility(ability.setAbilityWord(AbilityWord.VIVID).addHint(ColorsAmongControlledPermanentsCount.ALL_PERMANENTS.getHint()));
    }

    private Prismabasher(final Prismabasher card) {
        super(card);
    }

    @Override
    public Prismabasher copy() {
        return new Prismabasher(this);
    }
}
