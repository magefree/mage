package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author jmharmon
 */

public final class BrashTaunter extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(AnotherPredicate.instance);
    }

    public BrashTaunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // Whenever Brash Taunter is dealt damage, it deals that much damage to target opponent.
        Ability ability = new DealtDamageToSourceTriggeredAbility(new BrashTaunterEffect(), false, false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // {2}{R}, {T}: Brash Taunter fights another target creature.
        Ability ability1 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new FightTargetSourceEffect(), new ManaCostsImpl("{2}{R}"));
        ability1.addCost(new TapSourceCost());
        ability1.addTarget(new TargetPermanent(filter));
        this.addAbility(ability1);
    }

    private BrashTaunter(final BrashTaunter card) {
        super(card);
    }

    @Override
    public BrashTaunter copy() {
        return new BrashTaunter(this);
    }
}

class BrashTaunterEffect extends OneShotEffect {

    public BrashTaunterEffect() {
        super(Outcome.Damage);
        this.staticText = "it deals that much damage to target opponent";
    }

    public BrashTaunterEffect(final BrashTaunterEffect effect) {
        super(effect);
    }

    @Override
    public BrashTaunterEffect copy() {
        return new BrashTaunterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = (Integer) getValue("damage");
        if (amount > 0) {
            Player player = game.getPlayer(targetPointer.getFirst(game, source));
            if (player != null) {
                player.damage(amount, source.getSourceId(), source, game);
                return true;
            }
        }
        return false;
    }
}
