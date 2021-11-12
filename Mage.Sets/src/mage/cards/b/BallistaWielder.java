package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.keyword.NightboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
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
public final class BallistaWielder extends CardImpl {

    public BallistaWielder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
        this.color.setRed(true);
        this.nightCard = true;

        // {2}{R}: Ballista Wielder deals 1 damage to any target. A creature dealt damage this way can't block this turn.
        Ability ability = new SimpleActivatedAbility(new BallistaWielderEffect(), new ManaCostsImpl<>("{2}{R}"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // Nightbound
        this.addAbility(new NightboundAbility());
    }

    private BallistaWielder(final BallistaWielder card) {
        super(card);
    }

    @Override
    public BallistaWielder copy() {
        return new BallistaWielder(this);
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
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            Player player = game.getPlayer(source.getFirstTarget());
            return player != null && player.damage(1, source, game) > 0;
        }
        if (permanent.damage(1, source, game) > 0) {
            game.addEffect(new CantBlockTargetEffect(Duration.EndOfTurn), source);
            return true;
        }
        return false;
    }
}
