package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.NightboundAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BallistaWatcher extends TransformingDoubleFacedCard {

    public BallistaWatcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.SOLDIER, SubType.WEREWOLF}, "{2}{R}{R}",
                "Ballista Wielder",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "R");

        // Ballista Watcher
        this.getLeftHalfCard().setPT(4, 3);

        // {2}{R}, {T}: Ballista Watcher deals 1 damage to any target.
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(1), new ManaCostsImpl<>("{2}{R}")
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.getLeftHalfCard().addAbility(ability);

        // Daybound
        this.getLeftHalfCard().addAbility(new DayboundAbility());

        // Ballista Wielder
        this.getRightHalfCard().setPT(5, 5);

        // {2}{R}: Ballista Wielder deals 1 damage to any target. A creature dealt damage this way can't block this turn.
        Ability weilderAbility = new SimpleActivatedAbility(new BallistaWielderEffect(), new ManaCostsImpl<>("{2}{R}"));
        weilderAbility.addTarget(new TargetAnyTarget());
        this.getRightHalfCard().addAbility(weilderAbility);

        // Nightbound
        this.getRightHalfCard().addAbility(new NightboundAbility());
    }

    private BallistaWatcher(final BallistaWatcher card) {
        super(card);
    }

    @Override
    public BallistaWatcher copy() {
        return new BallistaWatcher(this);
    }
}

class BallistaWielderEffect extends OneShotEffect {

    BallistaWielderEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals 1 damage to any target. A creature dealt damage this way can't block this turn";
    }

    private BallistaWielderEffect(final BallistaWielderEffect effect) {
        super(effect);
    }

    @Override
    public BallistaWielderEffect copy() {
        return new BallistaWielderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
            return player != null && player.damage(1, source, game) > 0;
        }
        if (permanent.damage(1, source, game) <= 0) {
            return false;
        }
        game.addEffect(new CantBlockTargetEffect(Duration.EndOfTurn), source);
        return true;
    }
}
