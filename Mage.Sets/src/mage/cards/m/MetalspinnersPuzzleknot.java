package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class MetalspinnersPuzzleknot extends CardImpl {

    public MetalspinnersPuzzleknot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // When Metalspinner's Puzzleknot enters the battlefield, you draw a card and you lose 1 life.
        Effect drawEffect = new DrawCardSourceControllerEffect(1, "you");
        Ability ability = new EntersBattlefieldTriggeredAbility(drawEffect);
        Effect lifeEffect = new LoseLifeSourceControllerEffect(1);
        ability.addEffect(lifeEffect.concatBy("and"));
        this.addAbility(ability);

        // {2}{B}, Sacrifice Metalspinner's Puzzleknot: You draw a card and you lose 1 life.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, drawEffect, new ManaCostsImpl<>("{2}{B}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(lifeEffect);
        this.addAbility(ability);
    }

    private MetalspinnersPuzzleknot(final MetalspinnersPuzzleknot card) {
        super(card);
    }

    @Override
    public MetalspinnersPuzzleknot copy() {
        return new MetalspinnersPuzzleknot(this);
    }
}
