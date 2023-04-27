package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class BorosLocket extends CardImpl {

    public BorosLocket(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Add {R} or {W}.
        this.addAbility(new RedManaAbility());
        this.addAbility(new WhiteManaAbility());

        // {R/W}{R/W}{R/W}{R/W}, {T}, Sacrifice Boros Locket: Draw two cards.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(2),
                new ManaCostsImpl<>("{R/W}{R/W}{R/W}{R/W}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private BorosLocket(final BorosLocket card) {
        super(card);
    }

    @Override
    public BorosLocket copy() {
        return new BorosLocket(this);
    }
}
