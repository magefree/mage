package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.OpponentsCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.SoldierToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CompanyCommander extends CardImpl {

    public CompanyCommander(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Command Section -- When Company Commander enters the battlefield, create a number of 1/1 white Soldier creature tokens equal to the number of opponents you have.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(
                new SoldierToken(), OpponentsCount.instance
        ).setText("create a number of 1/1 white Soldier creature tokens " +
                "equal to the number of opponents you have")).withFlavorWord("Command Section"));

        // Bring it Down! -- Whenever Company Commander attacks, creatures you control gain deathtouch until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new GainAbilityControlledEffect(
                DeathtouchAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES
        )).withFlavorWord("Bring it Down!"));
    }

    private CompanyCommander(final CompanyCommander card) {
        super(card);
    }

    @Override
    public CompanyCommander copy() {
        return new CompanyCommander(this);
    }
}
