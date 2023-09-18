package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.GoadedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VengefulAncestor extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a goaded creature");

    static {
        filter.add(GoadedPredicate.instance);
    }

    public VengefulAncestor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Vengeful Ancestor enters the battlefield or attacks, goad target creature.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new GoadTargetEffect());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Whenever a goaded creature attacks, it deals 1 damage to its controller.
        this.addAbility(new AttacksAllTriggeredAbility(
                new VengefulAncestorEffect(), false, filter,
                SetTargetPointer.NONE, false
        ));
    }

    private VengefulAncestor(final VengefulAncestor card) {
        super(card);
    }

    @Override
    public VengefulAncestor copy() {
        return new VengefulAncestor(this);
    }
}

class VengefulAncestorEffect extends OneShotEffect {

    VengefulAncestorEffect() {
        super(Outcome.Benefit);
        staticText = "it deals 1 damage to its controller";
    }

    private VengefulAncestorEffect(final VengefulAncestorEffect effect) {
        super(effect);
    }

    @Override
    public VengefulAncestorEffect copy() {
        return new VengefulAncestorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) getValue("attacker");
        if (permanent == null) {
            return false;
        }
        Player player = game.getPlayer(permanent.getControllerId());
        if (player == null) {
            return false;
        }
        return player.damage(1, permanent.getId(), source, game) > 0;
    }
}
