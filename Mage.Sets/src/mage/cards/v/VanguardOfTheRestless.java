package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CommanderCastFromCommandZoneValue;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class VanguardOfTheRestless extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.SPIRIT);

    public VanguardOfTheRestless(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Spirits you control get +1/+1 for each time you've cast your commander from the command zone this game.
        this.addAbility(new SimpleStaticAbility(
            new BoostControlledEffect(
                CommanderCastFromCommandZoneValue.instance,
                CommanderCastFromCommandZoneValue.instance,
                Duration.WhileOnBattlefield,
                filter,
                false
            ).setText("Spirits you control get +1/+1 for each time you've cast your commander from the command zone this game.")
        ).addHint(CommanderCastFromCommandZoneValue.getHint()));

        // Whenever a Spirit you control enters, you may pay {2}{W}. If you do, return this card from your graveyard to the battlefield.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
            Zone.GRAVEYARD,
            new DoIfCostPaid(
                new ReturnSourceFromGraveyardToBattlefieldEffect(),
                new ManaCostsImpl<>("{2}{W}")
            ),
            filter,
            false
        ));
    }

    private VanguardOfTheRestless(final VanguardOfTheRestless card) {
        super(card);
    }

    @Override
    public VanguardOfTheRestless copy() {
        return new VanguardOfTheRestless(this);
    }
}
