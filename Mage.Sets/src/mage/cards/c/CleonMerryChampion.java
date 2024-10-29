package mage.cards.c;

import mage.MageInt;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.HeroicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CleonMerryChampion extends CardImpl {

    public CleonMerryChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Heroic -- Whenever you cast a spell that targets Cleon, exile the top card of your library. You may play that card until the end of your next turn.
        this.addAbility(new HeroicAbility(new ExileTopXMayPlayUntilEffect(1, Duration.UntilEndOfYourNextTurn)));
    }

    private CleonMerryChampion(final CleonMerryChampion card) {
        super(card);
    }

    @Override
    public CleonMerryChampion copy() {
        return new CleonMerryChampion(this);
    }
}
