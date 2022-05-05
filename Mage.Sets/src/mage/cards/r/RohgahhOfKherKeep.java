package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapAllEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainControlAllEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class RohgahhOfKherKeep extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures you control named Kobolds of Kher Keep");

    static {
        filter.add(new NamePredicate("Kobolds of Kher Keep"));
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public RohgahhOfKherKeep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}{R}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.KOBOLD);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // At the beginning of your upkeep, you may pay {R}{R}{R}. If you don't, tap Rohgahh of Kher Keep and all creatures named Kobolds of Kher Keep, then an opponent gains control of them.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new RohgahhOfKherKeepEffect(), TargetController.YOU, false));

        // Creatures you control named Kobolds of Kher Keep get +2/+2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(2, 2, Duration.WhileOnBattlefield, filter, false)));
    }

    private RohgahhOfKherKeep(final RohgahhOfKherKeep card) {
        super(card);
    }

    @Override
    public RohgahhOfKherKeep copy() {
        return new RohgahhOfKherKeep(this);
    }
}

class RohgahhOfKherKeepEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures named Kobolds of Kher Keep");

    static {
        filter.add(new NamePredicate("Kobolds of Kher Keep"));
    }

    RohgahhOfKherKeepEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may pay {R}{R}{R}. If you don't, tap {this} and all creatures named Kobolds of Kher Keep, then an opponent gains control of them.";
    }

    RohgahhOfKherKeepEffect(final RohgahhOfKherKeepEffect effect) {
        super(effect);
    }

    @Override
    public RohgahhOfKherKeepEffect copy() {
        return new RohgahhOfKherKeepEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player == null) {
            return false;
        }
        Cost cost = new ManaCostsImpl("{R}{R}{R}");
        if (!cost.canPay(source, source, player.getId(), game)
                || !player.chooseUse(Outcome.Benefit, "Pay {R}{R}{R}?", source, game)
                || !cost.pay(source, game, source, player.getId(), false)) {
            TargetOpponent target = new TargetOpponent();
            Player opponent = null;
            if (target.choose(Outcome.Detriment, player.getId(), source.getSourceId(), source, game)) {
                opponent = game.getPlayer(target.getFirstTarget());
            }
            new TapAllEffect(filter).apply(game, source);
            if (permanent != null) {
                permanent.tap(source, game);
            }
            if (opponent != null) {
                new GainControlAllEffect(Duration.Custom, filter, opponent.getId()).apply(game, source);
                if (permanent != null) {
                    ContinuousEffect effect = new GainControlTargetEffect(Duration.Custom, true, opponent.getId());
                    effect.setTargetPointer(new FixedTarget(permanent, game));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;
    }
}
