package mage.cards.t;

import mage.Mana;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.AttackedThisTurnOpponentsCount;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.watchers.common.PlayersAttackedThisTurnWatcher;

import java.util.UUID;

/**
 * @author muz
 */
public final class TheBeaconOfKahless extends CardImpl {

    public TheBeaconOfKahless(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // Battlefront -- {T}: Choose a color. Add an amount of mana of that color equal to the number of opponents you attacked this turn.
        this.addAbility(
            new DynamicManaAbility(
                Mana.AnyMana(1),
                AttackedThisTurnOpponentsCount.instance,
                new TapSourceCost(),
                "choose a color. Add an amount of mana of that color equal to the number of opponents you attacked this turn",
                true
            ).withFlavorWord("Battlefront").addHint(AttackedThisTurnOpponentsCount.getHint()),
            new PlayersAttackedThisTurnWatcher()
        );
    }

    private TheBeaconOfKahless(final TheBeaconOfKahless card) {
        super(card);
    }

    @Override
    public TheBeaconOfKahless copy() {
        return new TheBeaconOfKahless(this);
    }
}
