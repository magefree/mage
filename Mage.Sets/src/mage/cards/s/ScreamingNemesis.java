package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.CantGainLifeRestOfGameTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterAnyTarget;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ScreamingNemesis extends CardImpl {

    private static final FilterAnyTarget filter = new FilterAnyTarget("any other target");

    static {
        filter.getPermanentFilter().add(AnotherPredicate.instance);
    }

    public ScreamingNemesis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Screaming Nemesis is dealt damage, it deals that much damage to any other target. If a player is dealt damage this way, they can't gain life for the rest of the game.
        Ability ability = new DealtDamageToSourceTriggeredAbility(new ScreamingNemesisEffect(), false);
        ability.addTarget(new TargetAnyTarget(filter));
        this.addAbility(ability);
    }

    private ScreamingNemesis(final ScreamingNemesis card) {
        super(card);
    }

    @Override
    public ScreamingNemesis copy() {
        return new ScreamingNemesis(this);
    }
}

class ScreamingNemesisEffect extends OneShotEffect {

    ScreamingNemesisEffect() {
        super(Outcome.Damage);
        staticText = "it deals that much damage to any other target. "
                + "If a player is dealt damage this way, they can't gain life for the rest of the game";
    }

    private ScreamingNemesisEffect(final ScreamingNemesisEffect effect) {
        super(effect);
    }

    @Override
    public ScreamingNemesisEffect copy() {
        return new ScreamingNemesisEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = SavedDamageValue.MUCH.calculate(game, source, this);
        UUID targetId = getTargetPointer().getFirst(game, source);
        if (amount <= 0 || targetId == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(targetId);
        Player player = game.getPlayer(targetId);
        if (permanent != null) {
            permanent.damage(amount, source.getSourceId(), source, game, false, true);
            return true;
        } else if (player != null) {
            int damageDone = player.damage(amount, source.getSourceId(), source, game, false, true);
            if (damageDone > 0) {
                // If a player is dealt damage this way, they can't gain life for the rest of the game.
                game.addEffect(new CantGainLifeRestOfGameTargetEffect().setTargetPointer(new FixedTarget(player.getId())), source);
            }
            return true;
        }
        return false;
    }
}