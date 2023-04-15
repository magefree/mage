package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.TransformIntoSourceTriggeredAbility;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DoricOwlbearAvenger extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public DoricOwlbearAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.BEAR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);
        this.color.setGreen(true);
        this.nightCard = true;

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When this creature transforms into Doric, Owlbear Avenger, other legendary creatures you control get +2/+2 and gain trample until end of turn.
        Ability ability = new TransformIntoSourceTriggeredAbility(new BoostControlledEffect(
                2, 2, Duration.EndOfTurn, filter, true
        ).setText("other legendary creatures you control get +2/+2"));
        ability.addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn, filter, true
        ).setText("and gain trample until end of turn"));
        this.addAbility(ability);

        // At the beginning of your upkeep, transform Doric.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(), TargetController.YOU, false));
    }

    private DoricOwlbearAvenger(final DoricOwlbearAvenger card) {
        super(card);
    }

    @Override
    public DoricOwlbearAvenger copy() {
        return new DoricOwlbearAvenger(this);
    }
}
