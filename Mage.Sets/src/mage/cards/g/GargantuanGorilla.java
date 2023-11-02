package mage.cards.g;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author L_J
 */
public final class GargantuanGorilla extends CardImpl {

    public GargantuanGorilla(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}{G}");
        this.subtype.add(SubType.APE);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // At the beginning of your upkeep, you may sacrifice a Forest. If you sacrifice a snow Forest this way, Gargantuan Gorilla gains trample until end of turn. If you don’t sacrifice a Forest, sacrifice Gargantuan Gorilla and it deals 7 damage to you.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new GargantuanGorillaSacrificeEffect(), TargetController.YOU, false));

        // {T}: Gargantuan Gorilla deals damage equal to its power to another target creature. That creature deals damage equal to its power to Gargantuan Gorilla.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GargantuanGorillaFightEffect(), new TapSourceCost());
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(AnotherPredicate.instance);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private GargantuanGorilla(final GargantuanGorilla card) {
        super(card);
    }

    @Override
    public GargantuanGorilla copy() {
        return new GargantuanGorilla(this);
    }
}

class GargantuanGorillaSacrificeEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Forest");
    private static final FilterPermanent filterSnow = new FilterPermanent("snow permanent");

    static {
        filter.add(SubType.FOREST.getPredicate());
        filterSnow.add(SuperType.SNOW.getPredicate());
    }

    public GargantuanGorillaSacrificeEffect() {
        super(Outcome.Sacrifice);
        staticText = "you may sacrifice a Forest. If you sacrifice a snow Forest this way, {this} gains trample until end of turn. If you don't sacrifice a Forest, sacrifice {this} and it deals 7 damage to you.";
    }

    private GargantuanGorillaSacrificeEffect(final GargantuanGorillaSacrificeEffect effect) {
        super(effect);
    }

    @Override
    public GargantuanGorillaSacrificeEffect copy() {
        return new GargantuanGorillaSacrificeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            TargetControlledPermanent target = new TargetControlledPermanent(1, 1, filter, true);
            SacrificeTargetCost cost = new SacrificeTargetCost(target);
            if (!controller.chooseUse(Outcome.Benefit, "Sacrifice a Forest?", source, game)
                    || !cost.canPay(source, source, source.getControllerId(), game)
                    || !cost.pay(source, game, source, source.getControllerId(), true)) {
                sourcePermanent.sacrifice(source, game);
                controller.damage(7, sourcePermanent.getId(), source, game);
            } else if (cost.isPaid()) {
                for (Permanent permanent : cost.getPermanents()) {
                    if (filterSnow.match(permanent, game)) {
                        game.addEffect(new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.EndOfTurn), source);
                        break;
                    }
                }
            }
            return true;
        }
        return false;
    }
}

class GargantuanGorillaFightEffect extends OneShotEffect {

    public GargantuanGorillaFightEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals damage equal to its power to another target creature. That creature deals damage equal to its power to {this}";
    }

    private GargantuanGorillaFightEffect(final GargantuanGorillaFightEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject != null) {
            Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
            Permanent creature1 = game.getPermanent(getTargetPointer().getFirst(game, source));
            // 20110930 - 701.10
            if (creature1 != null && sourcePermanent != null) {
                if (creature1.isCreature(game) && sourcePermanent.isCreature(game)) {
                    sourcePermanent.damage(creature1.getPower().getValue(), creature1.getId(), source, game, false, true);
                    creature1.damage(sourcePermanent.getPower().getValue(), sourcePermanent.getId(), source, game, false, true);
                    return true;
                }
            }
            if (!game.isSimulation()) {
                game.informPlayers(sourceObject.getLogName() + ": Fighting effect has been fizzled.");
            }
        }
        return false;
    }

    @Override
    public GargantuanGorillaFightEffect copy() {
        return new GargantuanGorillaFightEffect(this);
    }
}
