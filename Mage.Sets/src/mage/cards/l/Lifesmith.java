package mage.cards.l;

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
import mage.players.Player;
import mage.util.ManaUtil;

import java.util.UUID;

/**
 * @author Loki
 */
public final class Lifesmith extends CardImpl {
    public Lifesmith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.addAbility(new SpellCastControllerTriggeredAbility(new LifesmithEffect(), StaticFilters.FILTER_SPELL_AN_ARTIFACT, false));
    }

    private Lifesmith(final Lifesmith card) {
        super(card);
    }

    @Override
    public Lifesmith copy() {
        return new Lifesmith(this);
    }
}

class LifesmithEffect extends OneShotEffect {
    LifesmithEffect() {
        super(Outcome.GainLife);
        staticText = "you may pay {1}. If you do, you gain 3 life";
    }

    LifesmithEffect(final LifesmithEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Cost cost = ManaUtil.createManaCost(1, false);
        if (cost.pay(source, game, source, source.getControllerId(), false, null)) {
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                player.gainLife(3, game, source);
                return true;
            }
        }
        return false;
    }

    @Override
    public LifesmithEffect copy() {
        return new LifesmithEffect(this);
    }
}
