package mage.cards.r;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.effects.mana.AddManaInAnyCombinationEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Zone;

/**
 * @author rullinoiz
 */
public final class RelicOfSauron extends CardImpl {

    public RelicOfSauron(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");
        

        // {T}: Add two mana in any combination of {U}, {B}, and/or {R}.
        this.addAbility(new SimpleManaAbility(
                Zone.BATTLEFIELD,
                new AddManaInAnyCombinationEffect(2, ColoredManaSymbol.U, ColoredManaSymbol.B, ColoredManaSymbol.R),
                new TapSourceCost()
        ));

        // {3}, {T}: Draw two cards, then discard a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(2),
                new GenericManaCost(3)
        );
        ability.addCost(new TapSourceCost());
        ability.addEffect(new DiscardControllerEffect(1).concatBy(", then"));
        this.addAbility(ability);

    }

    private RelicOfSauron(final RelicOfSauron card) {
        super(card);
    }

    @Override
    public RelicOfSauron copy() {
        return new RelicOfSauron(this);
    }
}
