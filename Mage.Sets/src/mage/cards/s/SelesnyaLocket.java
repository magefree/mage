package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class SelesnyaLocket extends CardImpl {

    public SelesnyaLocket(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Add {G} or {W}.
        this.addAbility(new GreenManaAbility());
        this.addAbility(new WhiteManaAbility());

        // {G/W}{G/W}{G/W}{G/W}, {T}, Sacrifice Selesnya Locket: Draw two cards.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(2),
                new ManaCostsImpl<>("{G/W}{G/W}{G/W}{G/W}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private SelesnyaLocket(final SelesnyaLocket card) {
        super(card);
    }

    @Override
    public SelesnyaLocket copy() {
        return new SelesnyaLocket(this);
    }
}
