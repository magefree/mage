package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.StitcherGeralfZombieToken;

/**
 *
 * @author weirddan455
 */
public final class GeralfVisionaryStitcher extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.ZOMBIE, "Zombies");
    private static final FilterControlledCreaturePermanent filter2
            = new FilterControlledCreaturePermanent("another nontoken creature");

    static {
        filter2.add(AnotherPredicate.instance);
        filter2.add(TokenPredicate.FALSE);
    }

    public GeralfVisionaryStitcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Zombies you control have flying.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield, filter)));

        // {U}, {T}, Sacrifice another nontoken creature: Create an X/X blue Zombie token, where X is the sacrificed creature's power.
        Ability ability = new SimpleActivatedAbility(new GeralfVisionaryStitcherEffect(), new ManaCostsImpl<>("{U}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(filter2));
        this.addAbility(ability);
    }

    private GeralfVisionaryStitcher(final GeralfVisionaryStitcher card) {
        super(card);
    }

    @Override
    public GeralfVisionaryStitcher copy() {
        return new GeralfVisionaryStitcher(this);
    }
}

class GeralfVisionaryStitcherEffect extends OneShotEffect {

    public GeralfVisionaryStitcherEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Create an X/X blue Zombie token, where X is the sacrificed creature's power";
    }

    private GeralfVisionaryStitcherEffect(final GeralfVisionaryStitcherEffect effect) {
        super(effect);
    }

    @Override
    public GeralfVisionaryStitcherEffect copy() {
        return new GeralfVisionaryStitcherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = 0;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof SacrificeTargetCost) {
                for (Permanent permanent : ((SacrificeTargetCost) cost).getPermanents()) {
                    xValue += permanent.getPower().getValue();
                }
            }
        }
        return new StitcherGeralfZombieToken(xValue).putOntoBattlefield(1, game, source, source.getControllerId());
    }
}
