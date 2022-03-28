package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.LoseCreatureTypeSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class MogisGodOfSlaughter extends CardImpl {

    public MogisGodOfSlaughter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{2}{B}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);

        this.power = new MageInt(7);
        this.toughness = new MageInt(5);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // As long as your devotion to black and red is less than seven, Mogis isn't a creature.
        this.addAbility(new SimpleStaticAbility(new LoseCreatureTypeSourceEffect(DevotionCount.BR, 7))
                .addHint(DevotionCount.BR.getHint()));

        // At the beginning of each opponent's upkeep, Mogis deals 2 damage to that player unless they sacrifice a creature.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                Zone.BATTLEFIELD, new MogisGodOfSlaughterEffect(),
                TargetController.OPPONENT, false, true
        );
        this.addAbility(ability);
    }

    private MogisGodOfSlaughter(final MogisGodOfSlaughter card) {
        super(card);
    }

    @Override
    public MogisGodOfSlaughter copy() {
        return new MogisGodOfSlaughter(this);
    }
}

class MogisGodOfSlaughterEffect extends OneShotEffect {

    MogisGodOfSlaughterEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals 2 damage to that player unless they sacrifice a creature";
    }

    private MogisGodOfSlaughterEffect(final MogisGodOfSlaughterEffect effect) {
        super(effect);
    }

    @Override
    public MogisGodOfSlaughterEffect copy() {
        return new MogisGodOfSlaughterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        if (player == null) {
            return false;
        }
        if (game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_CREATURE, game.getActivePlayerId(), game) == 0) {
            return player.damage(2, source.getSourceId(), source, game) > 0;
        }
        TargetPermanent target = new TargetControlledCreaturePermanent(1);
        target.setNotTarget(true);
        if (target.canChoose(player.getId(), source, game)
                && player.chooseUse(Outcome.Detriment, "Sacrifice a creature to prevent 2 damage?", source, game)
                && player.choose(Outcome.Sacrifice, target, source, game)) {
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null && permanent.sacrifice(source, game)) {
                return true;
            }
        }
        return player.damage(2, source.getSourceId(), source, game) > 0;
    }
}