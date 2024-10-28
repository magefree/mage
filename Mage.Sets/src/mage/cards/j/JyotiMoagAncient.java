package mage.cards.j;

import mage.MageInt;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.CommanderCastFromCommandZoneValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.ForestDryadToken;

import java.util.UUID;

/**
 *
 * @author grimreap124
 */
public final class JyotiMoagAncient extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("land creatures");

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
                new BoostControlledEffect(SourcePermanentPowerValue.NOT_NEGATIVE, SourcePermanentPowerValue.NOT_NEGATIVE, Duration.EndOfTurn, filter, false),
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
