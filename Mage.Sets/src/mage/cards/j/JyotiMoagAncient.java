package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.CommanderCastFromCommandZoneValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.game.permanent.token.ForestDryadToken;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author grimreap124
 */
public final class JyotiMoagAncient extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("land creatures");

    private static final SourcePermanentPowerCount xValue = new SourcePermanentPowerCount();

    static {
        filter.add(CardType.LAND.getPredicate());

    }

    public JyotiMoagAncient(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.CREATURE }, "{2}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When Jyoti, Moag Ancient enters the battlefield, create a 1/1 green Forest Dryad land creature token
        // for each time you've cast your commander from the command zone this game.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(new ForestDryadToken(), CommanderCastFromCommandZoneValue.instance).setText(
                        "create a 1/1 green Forest Dryad land creature token for each time you've cast your commander from the command zone this game"))
                .addHint(CommanderCastFromCommandZoneValue.getHint()));

        // At the beginning of each combat, land creatures you control get +X/+X until end of turn, where X is Jyoti's power.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                new BoostControlledEffect(xValue, xValue, Duration.EndOfTurn, filter, false),
                TargetController.ANY, false));
    }

    private JyotiMoagAncient(final JyotiMoagAncient card) {
        super(card);
    }

    @Override
    public JyotiMoagAncient copy() {
        return new JyotiMoagAncient(this);
    }
}