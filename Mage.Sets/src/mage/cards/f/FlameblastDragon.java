package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Loki
 */
public final class FlameblastDragon extends CardImpl {

    public FlameblastDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Flameblast Dragon attacks, you may pay {X}{R}. If you do, Flameblast Dragon deals X damage to any target.
        Ability ability = new AttacksTriggeredAbility(new FlameblastDragonEffect(), false);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private FlameblastDragon(final FlameblastDragon card) {
        super(card);
    }

    @Override
    public FlameblastDragon copy() {
        return new FlameblastDragon(this);
    }
}

class FlameblastDragonEffect extends OneShotEffect {

    FlameblastDragonEffect() {
        super(Outcome.Benefit);
        staticText = "you may pay {X}{R}. If you do, {this} deals X damage to any target";
    }

    FlameblastDragonEffect(final FlameblastDragonEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        ManaCosts cost = new ManaCostsImpl<>("{X}{R}");
        if (player != null) {
            if (player.chooseUse(Outcome.Damage, "Pay " + cost.getText() + "? If you do, Flameblast Dragon deals X damage to any target", source, game)) {
                int costX = player.announceXMana(0, Integer.MAX_VALUE, "Announce the value for {X}", game, source);
                cost.add(new GenericManaCost(costX));
                if (cost.pay(source, game, source, source.getControllerId(), false, null)) {
                    Permanent permanent = game.getPermanent(source.getFirstTarget());
                    if (permanent != null) {
                        permanent.damage(costX, source.getSourceId(), source, game, false, true);
                        return true;
                    }
                    Player targetPlayer = game.getPlayer(source.getFirstTarget());
                    if (targetPlayer != null) {
                        targetPlayer.damage(costX, source.getSourceId(), source, game);
                        return true;
                    }
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public FlameblastDragonEffect copy() {
        return new FlameblastDragonEffect(this);
    }

}
