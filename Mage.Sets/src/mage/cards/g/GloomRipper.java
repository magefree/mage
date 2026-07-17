package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.AdditiveDynamicValue;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author muz
 */
public final class GloomRipper extends CardImpl {

    private static final FilterPermanent creatureFilter = new FilterControlledPermanent(SubType.ELF, "Elves you control");
    private static final FilterCard cardFilter = new FilterCard(SubType.ELF, "Elf cards");

    private static final DynamicValue xValuePos = new AdditiveDynamicValue(
            new PermanentsOnBattlefieldCount(creatureFilter),
            new CardsInControllerGraveyardCount(cardFilter)
    );
    private static final DynamicValue xValueNeg = new SignInversionDynamicValue(xValuePos);

    private static final Hint hint = new ValueHint("Elves you control plus the number of Elf cards in your graveyard", xValuePos);

    public GloomRipper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When this creature enters, target creature you control gets +X/+0 until end of turn and up to one target creature an opponent controls gets -0/-X until end of turn, where X is the number of Elves you control plus the number of Elf cards in your graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new BoostTargetEffect(xValuePos, StaticValue.get(0))
                        .setText("target creature you control gets +X/+0 until end of turn")
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        ability.addEffect(
                new BoostTargetEffect(StaticValue.get(0), xValueNeg)
                        .setText("and up to one target creature an opponent controls gets -0/-X until end of turn, " +
                                "where X is the number of Elves you control plus the number of Elf cards in your graveyard")
                        .setTargetPointer(new SecondTargetPointer())
        );
        ability.addTarget(new TargetOpponentsCreaturePermanent(0, 1));
        this.addAbility(ability.addHint(hint));
    }

    private GloomRipper(final GloomRipper card) {
        super(card);
    }

    @Override
    public GloomRipper copy() {
        return new GloomRipper(this);
    }
}
