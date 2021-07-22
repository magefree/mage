package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LetterOfAcceptance extends CardImpl {

    public LetterOfAcceptance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // {2}, {T}, Sacrifice Letter of Acceptance: Draw a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1), new GenericManaCost(2)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private LetterOfAcceptance(final LetterOfAcceptance card) {
        super(card);
    }

    @Override
    public LetterOfAcceptance copy() {
        return new LetterOfAcceptance(this);
    }
}
