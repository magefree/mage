package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.RegenerateTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Swarmyard extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Insect, Rat, Spider, or Squirrel");

    static {
        filter.add(Predicates.or(
                SubType.INSECT.getPredicate(),
                SubType.RAT.getPredicate(),
                SubType.SPIDER.getPredicate(),
                SubType.SQUIRREL.getPredicate()
        ));
    }

    public Swarmyard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {tap}: Regenerate target Insect, Rat, Spider, or Squirrel.
        Ability ability = new SimpleActivatedAbility(new RegenerateTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

    }

    private Swarmyard(final Swarmyard card) {
        super(card);
    }

    @Override
    public Swarmyard copy() {
        return new Swarmyard(this);
    }
}
