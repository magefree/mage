package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetSacrifice;

/**
 * @author muz
 */
public final class LordOfThePitToken extends TokenImpl {

    public LordOfThePitToken() {
        super("Lord of the Pit", "Lord of the Pit token");
        manaCost = new ManaCostsImpl<>("{4}{B}{B}");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.DEMON);
        power = new MageInt(7);
        toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of your upkeep, sacrifice a creature other than Lord of the Pit. If you can't, Lord of the Pit deals 7 damage to you.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new LordOfThePitEffect()));
    }

    private LordOfThePitToken(final LordOfThePitToken token) {
        super(token);
    }

    @Override
    public LordOfThePitToken copy() {
        return new LordOfThePitToken(this);
    }
}

class LordOfThePitEffect extends OneShotEffect {

    LordOfThePitEffect() {
        super(Outcome.Damage);
        this.staticText = "sacrifice a creature other than {this}. If you can't, {this} deals 7 damage to you.";
    }

    private LordOfThePitEffect(final LordOfThePitEffect effect) {
        super(effect);
    }

    @Override
    public LordOfThePitEffect copy() {
        return new LordOfThePitEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent == null) {
            sourcePermanent = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        }
        if (player == null || sourcePermanent == null) {
            return false;
        }

        FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature other than " + sourcePermanent.getName());
        filter.add(AnotherPredicate.instance);

        Target target = new TargetSacrifice(filter);
        if (target.canChoose(player.getId(), source, game)) {
            player.choose(Outcome.Sacrifice, target, source, game);
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                permanent.sacrifice(source, game);
                return true;
            }
        } else {
            player.damage(7, source.getSourceId(), source, game);
            return true;
        }
        return false;
    }
}
