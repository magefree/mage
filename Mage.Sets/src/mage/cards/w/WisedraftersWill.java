package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.PlayWithHandRevealedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class WisedraftersWill extends CardImpl {

    public WisedraftersWill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");

        // Your opponents play with their hands revealed.
        this.addAbility(new SimpleStaticAbility(new PlayWithHandRevealedEffect(TargetController.OPPONENT)));

        // {U}, Sacrifice Wisedrafter's Will: Draw a card.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{U}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

        // {U}{U}, Sacrifice Wisedrafter's Will: Counter target spell.
        ability = new SimpleActivatedAbility(new CounterTargetEffect(), new ManaCostsImpl<>("{U}{U}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetSpell());
        this.addAbility(ability);
    }

    private WisedraftersWill(final WisedraftersWill card) {
        super(card);
    }

    @Override
    public WisedraftersWill copy() {
        return new WisedraftersWill(this);
    }
}
