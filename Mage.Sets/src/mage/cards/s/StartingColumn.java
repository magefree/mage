package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.MaxSpeedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.keyword.StartYourEnginesAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StartingColumn extends CardImpl {

    public StartingColumn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Start your engines!
        this.addAbility(new StartYourEnginesAbility());

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // Max speed -- {T}, Sacrifice this artifact: Draw two cards, then discard a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawDiscardControllerEffect(2, 1), new TapSourceCost()
        );
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(new MaxSpeedAbility(ability));
    }

    private StartingColumn(final StartingColumn card) {
        super(card);
    }

    @Override
    public StartingColumn copy() {
        return new StartingColumn(this);
    }
}
