
package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class LifesparkSpellbomb extends CardImpl {

    public LifesparkSpellbomb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {G}, Sacrifice Lifespark Spellbomb: Until end of turn, target land becomes a 3/3 creature that's still a land.
        Ability firstAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BecomesCreatureTargetEffect(
                        new CreatureToken(3, 3),
                        false, true, Duration.EndOfTurn)
                        .withDurationRuleAtStart(true), new ColoredManaCost(ColoredManaSymbol.G));
        firstAbility.addCost(new SacrificeSourceCost());
        firstAbility.addTarget(new TargetLandPermanent());
        this.addAbility(firstAbility);

        // {1}, Sacrifice Lifespark Spellbomb: Draw a card.
        Ability secondAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new GenericManaCost(1));
        secondAbility.addCost(new SacrificeSourceCost());
        this.addAbility(secondAbility);
    }

    private LifesparkSpellbomb(final LifesparkSpellbomb card) {
        super(card);
    }

    @Override
    public LifesparkSpellbomb copy() {
        return new LifesparkSpellbomb(this);
    }

}