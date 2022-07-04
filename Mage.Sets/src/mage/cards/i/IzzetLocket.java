package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class IzzetLocket extends CardImpl {

    public IzzetLocket(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Add {U} or {R}.
        this.addAbility(new BlueManaAbility());
        this.addAbility(new RedManaAbility());

        // {U/R}{U/R}{U/R}{U/R}, {T}, Sacrifice Izzet Locket: Draw two cards.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(2),
                new ManaCostsImpl<>("{U/R}{U/R}{U/R}{U/R}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private IzzetLocket(final IzzetLocket card) {
        super(card);
    }

    @Override
    public IzzetLocket copy() {
        return new IzzetLocket(this);
    }
}
