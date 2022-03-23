
package mage.cards.d;

import java.util.Locale;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author jeffwadsworth
 */
public final class DemonicHordes extends CardImpl {

    public DemonicHordes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}{B}");
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // {tap}: Destroy target land.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(new FilterLandPermanent()));
        this.addAbility(ability);

        // At the beginning of your upkeep, unless you pay {B}{B}{B}, tap Demonic Hordes and sacrifice a land of an opponent's choice.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DemonicHordesEffect(new ManaCostsImpl("{B}{B}{B}")), TargetController.YOU, false));
    }

    private DemonicHordes(final DemonicHordes card) {
        super(card);
    }

    @Override
    public DemonicHordes copy() {
        return new DemonicHordes(this);
    }
}

class DemonicHordesEffect extends OneShotEffect {

    protected Cost cost;

    public DemonicHordesEffect(Cost cost) {
        super(Outcome.Sacrifice);
        this.cost = cost;
        staticText = "unless you pay {B}{B}{B}, tap {this} and sacrifice a land of an opponent's choice";
    }

    public DemonicHordesEffect(final DemonicHordesEffect effect) {
        super(effect);
        this.cost = effect.cost.copy();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent demonicHordes = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && demonicHordes != null) {
            StringBuilder sb = new StringBuilder(cost.getText()).append('?');
            if (!sb.toString().toLowerCase(Locale.ENGLISH).startsWith("exile ") && !sb.toString().toLowerCase(Locale.ENGLISH).startsWith("return ")) {
                sb.insert(0, "Pay ");
            }
            if (controller.chooseUse(Outcome.Benefit, sb.toString(), source, game)) {
                cost.clearPaid();
                if (cost.pay(source, game, source, source.getControllerId(), false, null)) {
                    return true;
                }
            }
            demonicHordes.tap(source, game);
            Target choiceOpponent = new TargetOpponent();
            choiceOpponent.setNotTarget(true);
            FilterLandPermanent filterLand = new FilterLandPermanent();
            filterLand.add(new ControllerIdPredicate(source.getControllerId()));
            if (controller.choose(Outcome.Neutral, choiceOpponent, source, game)) {
                Player opponent = game.getPlayer(choiceOpponent.getFirstTarget());
                if (opponent != null) {
                    Target chosenLand = new TargetLandPermanent(filterLand);
                    chosenLand.setNotTarget(true);
                    if (opponent.chooseTarget(Outcome.Sacrifice, chosenLand, source, game)) {
                        Permanent land = game.getPermanent(chosenLand.getFirstTarget());
                        if (land != null) {
                            land.sacrifice(source, game);
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public DemonicHordesEffect copy() {
        return new DemonicHordesEffect(this);
    }
}
