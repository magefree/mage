package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MorphAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author djbrez
 */
public final class LiegeOfThePit extends CardImpl {

    public LiegeOfThePit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}{B}");
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // At the beginning of your upkeep, sacrifice a creature other than Liege of the Pit. If you can't, Liege of the Pit deals 7 damage to you.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new LiegeOfThePitEffect(), TargetController.YOU, false));
        // Morph {B}{B}{B}{B}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{B}{B}{B}{B}")));
    }

    private LiegeOfThePit(final LiegeOfThePit card) {
        super(card);
    }

    @Override
    public LiegeOfThePit copy() {
        return new LiegeOfThePit(this);
    }
}


class LiegeOfThePitEffect extends OneShotEffect {

    public LiegeOfThePitEffect() {
        super(Outcome.Damage);
        this.staticText = "sacrifice a creature other than {this}. If you can't, {this} deals 7 damage to you.";
    }

    public LiegeOfThePitEffect(final LiegeOfThePitEffect effect) {
        super(effect);
    }

    @Override
    public LiegeOfThePitEffect copy() {
        return new LiegeOfThePitEffect(this);
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

        Target target = new TargetControlledCreaturePermanent(1, 1, filter, true);
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
