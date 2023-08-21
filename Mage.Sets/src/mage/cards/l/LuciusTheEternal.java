package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author Xanderhall
 */
public final class LuciusTheEternal extends CardImpl {

    public LuciusTheEternal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{R}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ASTARTES);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Armor of Shrieking Souls -- When Lucius the Eternal dies, exile it and choose target creature an opponent controls. When that creature leaves the battlefield, return Lucius the Eternal from exile to the battlefield under its owner's control.
        this.addAbility(new DiesSourceTriggeredAbility(new LuciusTheEternalEffect()).withFlavorWord("Armor of Shrieking Souls"));
    }

    private LuciusTheEternal(final LuciusTheEternal card) {
        super(card);
    }

    @Override
    public LuciusTheEternal copy() {
        return new LuciusTheEternal(this);
    }
}

class LuciusTheEternalEffect extends OneShotEffect {

    LuciusTheEternalEffect() {
        super(Outcome.Benefit);
        this.staticText = "and choose target creature an opponent controls. When that creature leaves the battlefield, return Lucius the Eternal from exile to the battlefield under its owner's control.";
    }

    private LuciusTheEternalEffect(final LuciusTheEternalEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Target target = new TargetOpponentsCreaturePermanent();

        if (player == null || !player.chooseTarget(Outcome.AddAbility, new TargetOpponentsCreaturePermanent(), source, game)) {
            return false;
        }

        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null) {
            return false;
        }

        Effect effect = new ExileUntilSourceLeavesEffect(Zone.BATTLEFIELD);
        effect.setTargetPointer(new FixedTarget(source.getSourceId(), game));
        // Need to apply effect with correct source.
        // effect.apply(game, source);
        return true;
    }

    @Override
    public LuciusTheEternalEffect copy() {
        return new LuciusTheEternalEffect(this);
    }
}