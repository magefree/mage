package mage.cards.f;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.effects.keyword.VentureIntoTheDungeonEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class FiftyFeetOfRope extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.WALL, "Wall");

    public FiftyFeetOfRope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // Climb Over — {T}: Target Wall can't block this turn.
        Ability ability = new SimpleActivatedAbility(new CantBlockTargetEffect(Duration.EndOfTurn), new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability.withFlavorWord("Climb Over"));

        // Tie Up — {3}, {T}: Target creature doesn't untap during its controller's next untap step.
        ability = new SimpleActivatedAbility(new DontUntapInControllersNextUntapStepTargetEffect(), new ManaCostsImpl<>("{3}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability.withFlavorWord("Tie Up"));

        // Rappel Down — {4}, {T}: Venture into the dungeon. Activate only as a sorcery.
        ability = new ActivateAsSorceryActivatedAbility(new VentureIntoTheDungeonEffect(), new ManaCostsImpl<>("{4}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability.withFlavorWord("Rappel Down"));
    }

    private FiftyFeetOfRope(final FiftyFeetOfRope card) {
        super(card);
    }

    @Override
    public FiftyFeetOfRope copy() {
        return new FiftyFeetOfRope(this);
    }
}
