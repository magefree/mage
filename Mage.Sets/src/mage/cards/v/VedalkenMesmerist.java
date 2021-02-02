package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class VedalkenMesmerist extends CardImpl {

    public VedalkenMesmerist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Vedalken Mesmerist attacks, target creature an opponent controls gets -2/-0 until end of turn.
        Ability ability = new AttacksTriggeredAbility(
                new BoostTargetEffect(-2, 0, Duration.EndOfTurn), false
        );
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private VedalkenMesmerist(final VedalkenMesmerist card) {
        super(card);
    }

    @Override
    public VedalkenMesmerist copy() {
        return new VedalkenMesmerist(this);
    }
}
