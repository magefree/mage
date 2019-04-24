
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author LevelX2
 */
public final class BrionStoutarm extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature other than Brion Stoutarm");

    static {
        filter.add(new AnotherPredicate());
    }

    public BrionStoutarm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GIANT, SubType.WARRIOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());
        // {R}, {tap}, Sacrifice a creature other than Brion Stoutarm: Brion Stoutarm deals damage equal to the sacrificed creature's power to target player.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BrionStoutarmEffect(), new ManaCostsImpl("{R}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, filter, true)));
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);
    }

    public BrionStoutarm(final BrionStoutarm card) {
        super(card);
    }

    @Override
    public BrionStoutarm copy() {
        return new BrionStoutarm(this);
    }
}

class BrionStoutarmEffect extends OneShotEffect {

    public BrionStoutarmEffect() {
        super(Outcome.Benefit);
        this.staticText = "{this} deals damage equal to the sacrificed creature's power to target player or planeswalker";
    }

    public BrionStoutarmEffect(final BrionStoutarmEffect effect) {
        super(effect);
    }

    @Override
    public BrionStoutarmEffect copy() {
        return new BrionStoutarmEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = 0;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof SacrificeTargetCost && !((SacrificeTargetCost) cost).getPermanents().isEmpty()) {
                amount = ((SacrificeTargetCost) cost).getPermanents().get(0).getPower().getValue();
                break;
            }
        }
        if (amount > 0) {
            return new DamageTargetEffect(amount).apply(game, source);
        }
        return true;
    }
}
