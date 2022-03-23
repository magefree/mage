package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WitherAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class KulrathKnight extends CardImpl {

    public KulrathKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B/R}{B/R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Wither
        this.addAbility(WitherAbility.getInstance());

        // Creatures your opponents control with counters on them can't attack or block.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new KulrathKnightRestrictionEffect()));

    }

    private KulrathKnight(final KulrathKnight card) {
        super(card);
    }

    @Override
    public KulrathKnight copy() {
        return new KulrathKnight(this);
    }
}

class KulrathKnightRestrictionEffect extends RestrictionEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures your opponents control with counters on them");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(CounterAnyPredicate.instance);
    }

    public KulrathKnightRestrictionEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Creatures your opponents control with counters on them can't attack or block.";
    }

    public KulrathKnightRestrictionEffect(final KulrathKnightRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public KulrathKnightRestrictionEffect copy() {
        return new KulrathKnightRestrictionEffect(this);
    }

    @Override
    public boolean canAttack(Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return filter.match(permanent, source.getControllerId(), source, game);
    }
}
