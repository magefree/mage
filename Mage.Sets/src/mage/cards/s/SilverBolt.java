package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SilverBolt extends CardImpl {

    public SilverBolt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {3}, {T}, Sacrifice Silver Bolt: It deals 3 damage to target creature. If a Werewolf is dealt damage this way, destroy it.
        Ability ability = new SimpleActivatedAbility(new SilverBoltEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SilverBolt(final SilverBolt card) {
        super(card);
    }

    @Override
    public SilverBolt copy() {
        return new SilverBolt(this);
    }
}

class SilverBoltEffect extends OneShotEffect {

    SilverBoltEffect() {
        super(Outcome.Benefit);
        staticText = "it deals 3 damage to target creature. If a Werewolf is dealt damage this way, destroy it";
    }

    private SilverBoltEffect(final SilverBoltEffect effect) {
        super(effect);
    }

    @Override
    public SilverBoltEffect copy() {
        return new SilverBoltEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        if (permanent.damage(3, source, game) > 0
                && permanent.hasSubtype(SubType.WEREWOLF, game)) {
            permanent.destroy(source, game, false);
        }
        return true;
    }
}
