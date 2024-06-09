package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class LaserScrewdriver extends CardImpl {

    public LaserScrewdriver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // {1}, {T}: Tap target artifact.
        Ability ability = new SimpleActivatedAbility(
                new TapTargetEffect(),
                new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);

        // {2}, {T}: Surveil 1.
        ability = new SimpleActivatedAbility(
                new SurveilEffect(1),
                new GenericManaCost(2)
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {3}, {T}: Goad target creature.
        ability = new SimpleActivatedAbility(
                new GoadTargetEffect(),
                new GenericManaCost(3)
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private LaserScrewdriver(final LaserScrewdriver card) {
        super(card);
    }

    @Override
    public LaserScrewdriver copy() {
        return new LaserScrewdriver(this);
    }
}
