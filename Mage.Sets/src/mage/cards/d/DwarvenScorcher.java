package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DwarvenScorcher extends CardImpl {

    public DwarvenScorcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.DWARF);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Sacrifice Dwarven Scorcher: Dwarven Scorcher deals 1 damage to target creature unless that creature's controller has Dwarven Scorcher deal 2 damage to them.
        Ability ability = new SimpleActivatedAbility(new DwarvenScorcherEffect(), new SacrificeSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private DwarvenScorcher(final DwarvenScorcher card) {
        super(card);
    }

    @Override
    public DwarvenScorcher copy() {
        return new DwarvenScorcher(this);
    }
}

class DwarvenScorcherEffect extends OneShotEffect {

    DwarvenScorcherEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals 1 damage to target creature unless that creature's controller has {this} deal 2 damage to them";
    }

    private DwarvenScorcherEffect(final DwarvenScorcherEffect effect) {
        super(effect);
    }

    @Override
    public DwarvenScorcherEffect copy() {
        return new DwarvenScorcherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        Player player = game.getPlayer(permanent.getControllerId());
        if (player != null && player.chooseUse(outcome, "Have this spell deal 2 damage to you?", source, game)) {
            return player.damage(2, source.getSourceId(), source, game) > 0;
        }
        return permanent.damage(1, source.getSourceId(), source, game) > 0;
    }
}
