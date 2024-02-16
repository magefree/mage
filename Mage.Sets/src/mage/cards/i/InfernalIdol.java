package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InfernalIdol extends CardImpl {

    public InfernalIdol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());

        // {1}{B}{B}, {T}, Sacrifice Infernal Idol: You draw two cards and you lose 2 life.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(2, "you"), new ManaCostsImpl<>("{1}{B}{B}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(new LoseLifeSourceControllerEffect(2).concatBy("and"));
        this.addAbility(ability);
    }

    private InfernalIdol(final InfernalIdol card) {
        super(card);
    }

    @Override
    public InfernalIdol copy() {
        return new InfernalIdol(this);
    }
}
