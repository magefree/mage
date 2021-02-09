package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.BlockedByIdPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class WallOfCorpses extends CardImpl {

    public WallOfCorpses(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {B}, Sacrifice Wall of Corpses: Destroy target creature Wall of Corpses is blocking.
        FilterCreaturePermanent filter = new FilterCreaturePermanent("creature Wall of Corpses is blocking");
        filter.add(new BlockedByIdPredicate(this.getId()));
        Effect effect = new DestroyTargetEffect();
        SimpleActivatedAbility ability = new SimpleActivatedAbility(effect, new ManaCostsImpl("{B}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);

    }

    private WallOfCorpses(final WallOfCorpses card) {
        super(card);
    }

    @Override
    public WallOfCorpses copy() {
        return new WallOfCorpses(this);
    }
}
