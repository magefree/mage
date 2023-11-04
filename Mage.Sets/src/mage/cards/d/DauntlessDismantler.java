package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PermanentsEnterBattlefieldTappedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class DauntlessDismantler extends CardImpl {

    private static final FilterPermanent filter = new FilterArtifactPermanent("artifacts your opponents control");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public DauntlessDismantler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Artifacts your opponents control enter the battlefield tapped.
        this.addAbility(new SimpleStaticAbility(new PermanentsEnterBattlefieldTappedEffect(filter)));

        // {X}{X}{W}, Sacrifice Dauntless Dismantler: Destroy each artifact with mana value X.
        Ability ability = new SimpleActivatedAbility(
                new DauntlessDismantlerEffect(),
                new ManaCostsImpl<>("{X}{X}{W}")
        );
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private DauntlessDismantler(final DauntlessDismantler card) {
        super(card);
    }

    @Override
    public DauntlessDismantler copy() {
        return new DauntlessDismantler(this);
    }
}

/**
 * Inspired by {@link mage.cards.k.KarnsSylex}
 */
class DauntlessDismantlerEffect extends OneShotEffect {

    DauntlessDismantlerEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "destroy each artifact with mana value X";
    }

    private DauntlessDismantlerEffect(final DauntlessDismantlerEffect effect) {
        super(effect);
    }

    public DauntlessDismantlerEffect copy() {
        return new DauntlessDismantlerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterPermanent filter = new FilterArtifactPermanent();
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, source.getManaCostsToPay().getX()));

        boolean destroyed = false;
        for (Permanent permanent : game.getState().getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
            destroyed |= permanent.destroy(source, game);
        }
        return destroyed;
    }
}