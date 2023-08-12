package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;
import mage.util.ManaUtil;

import java.util.UUID;

/**
 * @author Loki, North
 */
public final class Embersmith extends CardImpl {
    public Embersmith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        SpellCastControllerTriggeredAbility ability = new SpellCastControllerTriggeredAbility(new EmbersmithEffect(), StaticFilters.FILTER_SPELL_AN_ARTIFACT, false);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private Embersmith(final Embersmith card) {
        super(card);
    }

    @Override
    public Embersmith copy() {
        return new Embersmith(this);
    }
}

class EmbersmithEffect extends OneShotEffect {
    EmbersmithEffect() {
        super(Outcome.Damage);
        staticText = "you may pay {1}. If you do, {this} deals 1 damage to any target";
    }

    EmbersmithEffect(final EmbersmithEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Cost cost = ManaUtil.createManaCost(1, false);
        if (cost.pay(source, game, source, source.getControllerId(), false, null)) {
            Permanent permanent = game.getPermanent(source.getFirstTarget());
            if (permanent != null) {
                permanent.damage(1, source.getSourceId(), source, game, false, true);
                return true;
            }
            Player player = game.getPlayer(source.getFirstTarget());
            if (player != null) {
                player.damage(1, source.getSourceId(), source, game);
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public EmbersmithEffect copy() {
        return new EmbersmithEffect(this);
    }
}
