package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.players.Player;

/**
 * @author LoneFox
 */
public final class BelzenlokDemonToken extends TokenImpl {

    public BelzenlokDemonToken() {
        super("Demon Token", "6/6 black Demon creature token with flying, trample, and "
                + "\"At the beginning of your upkeep, sacrifice another creature. If you can't, this creature deals 6 damage to you.\"");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.DEMON);
        power = new MageInt(6);
        toughness = new MageInt(6);
        addAbility(FlyingAbility.getInstance());
        addAbility(TrampleAbility.getInstance());
        addAbility(new BeginningOfUpkeepTriggeredAbility(new BelzenlokDemonTokenEffect(), TargetController.YOU, false));
    }

    public BelzenlokDemonToken(final BelzenlokDemonToken token) {
        super(token);
    }

    @Override
    public BelzenlokDemonToken copy() {
        return new BelzenlokDemonToken(this);
    }
}

class BelzenlokDemonTokenEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another creature");

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(AnotherPredicate.instance);
    }

    BelzenlokDemonTokenEffect() {
        super(Outcome.Benefit);
        this.staticText = "sacrifice another creature. If you can't, this creature deals 6 damage to you.";
    }

    BelzenlokDemonTokenEffect(final BelzenlokDemonTokenEffect effect) {
        super(effect);
    }

    @Override
    public BelzenlokDemonTokenEffect copy() {
        return new BelzenlokDemonTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int otherCreatures = new PermanentsOnBattlefieldCount(filter).calculate(game, source, this);
        if (otherCreatures > 0) {
            new SacrificeControllerEffect(filter, 1, "").apply(game, source);
        } else {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                controller.damage(6, source.getSourceId(), source, game);
            }
        }
        return true;
    }
}
