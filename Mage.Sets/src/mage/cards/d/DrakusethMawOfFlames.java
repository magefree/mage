package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePlayerOrPlaneswalker;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DrakusethMawOfFlames extends CardImpl {

    public DrakusethMawOfFlames(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Drakuseth, Maw of Flames attacks, it deals 4 damage to any target and 3 damage to each of up to two other targets.
        Ability ability = new AttacksTriggeredAbility(new DrakusethMawOfFlamesEffect(), false);
        Target target = new TargetAnyTarget().withChooseHint("to deal 4 damage");
        target.setTargetTag(1);
        ability.addTarget(target);
        FilterCreaturePlayerOrPlaneswalker filter = new FilterCreaturePlayerOrPlaneswalker("any target");
        filter.getPlayerFilter().add(new AnotherTargetPredicate(2, true));
        filter.getPermanentFilter().add(new AnotherTargetPredicate(2, true));
        target = new TargetAnyTarget(0, 2, filter).withChooseHint("to deal 3 damage");
        target.setTargetTag(2);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private DrakusethMawOfFlames(final DrakusethMawOfFlames card) {
        super(card);
    }

    @Override
    public DrakusethMawOfFlames copy() {
        return new DrakusethMawOfFlames(this);
    }
}

class DrakusethMawOfFlamesEffect extends OneShotEffect {

    DrakusethMawOfFlamesEffect() {
        super(Outcome.Damage);
        staticText = "it deals 4 damage to any target and 3 damage to each of "
                + "up to two other targets.";
    }

    private DrakusethMawOfFlamesEffect(final DrakusethMawOfFlamesEffect effect) {
        super(effect);
    }

    @Override
    public DrakusethMawOfFlamesEffect copy() {
        return new DrakusethMawOfFlamesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        damage(4, source.getTargets().get(0).getFirstTarget(), game, source);
        source.getTargets()
                .get(1)
                .getTargets()
                .stream()
                .forEach(targetId -> damage(3, targetId, game, source));
        return true;
    }

    private static void damage(int damage, UUID targetId, Game game, Ability source) {
        Permanent permanent = game.getPermanent(targetId);
        if (permanent != null) {
            permanent.damage(damage, source.getSourceId(), source, game, false, true);
        }
        Player player = game.getPlayer(targetId);
        if (player != null) {
            player.damage(damage, source.getSourceId(), source, game);
        }
    }
}
