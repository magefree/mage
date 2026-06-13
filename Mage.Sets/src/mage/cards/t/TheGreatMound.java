package mage.cards.t;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.VibraniumToken;

/**
 *
 * @author muz
 */
public final class TheGreatMound extends CardImpl {

    public TheGreatMound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {3}, {T}: Create a tapped Vibranium token.
        Ability ability = new SimpleActivatedAbility(
            new CreateTokenEffect(new VibraniumToken()),
            new ManaCostsImpl<>("{3}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {6}, {T}: Draw a card.
        ability = new SimpleActivatedAbility(
            new DrawCardSourceControllerEffect(1),
            new ManaCostsImpl<>("{6}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private TheGreatMound(final TheGreatMound card) {
        super(card);
    }

    @Override
    public TheGreatMound copy() {
        return new TheGreatMound(this);
    }
}
