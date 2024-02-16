package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.CitizenGreenWhiteToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DarlingOfTheMasses extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.CITIZEN, "Citizens");

    public DarlingOfTheMasses(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Other Citizens you control get +1/+0.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 0, Duration.WhileOnBattlefield, filter, true
        )));

        // Whenever Darling of the Masses attacks, create a 1/1 green and white Citizen creature token.
        this.addAbility(new AttacksTriggeredAbility(new CreateTokenEffect(new CitizenGreenWhiteToken())));
    }

    private DarlingOfTheMasses(final DarlingOfTheMasses card) {
        super(card);
    }

    @Override
    public DarlingOfTheMasses copy() {
        return new DarlingOfTheMasses(this);
    }
}
