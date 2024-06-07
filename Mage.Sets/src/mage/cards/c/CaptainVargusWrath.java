package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.CommanderCastCountValue;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CaptainVargusWrath extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.PIRATE, "");

    public CaptainVargusWrath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.CREATURE }, "{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Captain Vargus Wrath attacks, Pirates you control get +1/+1 until end of turn for each time you've cast a commander from the command zone this game.
        this.addAbility(new AttacksTriggeredAbility(new BoostControlledEffect(
                CommanderCastCountValue.instance, CommanderCastCountValue.instance,
                Duration.EndOfTurn, filter, false), false,
                "Whenever {this} attacks, Pirates you control get +1/+1 until end of turn " +
                        "for each time you've cast a commander from the command zone this game.")
                .addHint(CommanderCastCountValue.getHint()));
    }

    private CaptainVargusWrath(final CaptainVargusWrath card) {
        super(card);
    }

    @Override
    public CaptainVargusWrath copy() {
        return new CaptainVargusWrath(this);
    }
}