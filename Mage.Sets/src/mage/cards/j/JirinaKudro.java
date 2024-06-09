package mage.cards.j;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CommanderCastCountValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.HumanSoldierToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JirinaKudro extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.HUMAN, "Humans");

    public JirinaKudro(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Jirina Kudro enters the battlefield, create a 1/1 white Human Soldier creature token for each time you've cast a commander from the command zone this game.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(
                new HumanSoldierToken(), CommanderCastCountValue.instance
        ).setText("create a 1/1 white Human Soldier creature token for each time you've cast a commander from the command zone this game")).addHint(CommanderCastCountValue.getHint()));

        // Other Humans you control get +2/+0.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                2, 0, Duration.WhileOnBattlefield, filter, true
        )));
    }

    private JirinaKudro(final JirinaKudro card) {
        super(card);
    }

    @Override
    public JirinaKudro copy() {
        return new JirinaKudro(this);
    }
}
