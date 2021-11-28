package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrainingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class ApprenticeSharpshooter extends CardImpl {

    public ApprenticeSharpshooter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Training
        this.addAbility(new TrainingAbility());
    }

    private ApprenticeSharpshooter(final ApprenticeSharpshooter card) {
        super(card);
    }

    @Override
    public ApprenticeSharpshooter copy() {
        return new ApprenticeSharpshooter(this);
    }
}
