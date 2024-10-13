package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.util.CardUtil;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.delayed.WhenTargetLeavesBattlefieldDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;

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
        Ability ability = new DiesSourceTriggeredAbility(new LuciusTheEternalEffect());
        this.addAbility(ability.withFlavorWord("Armor of Shrieking Souls"));
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
        this.staticText = "exile it and choose target creature an opponent controls. When that creature leaves the battlefield, return {this} from exile to the battlefield under its owner's control.";
    }

    private LuciusTheEternalEffect(final LuciusTheEternalEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Target target = new TargetOpponentsCreaturePermanent();

        if (player == null || !player.chooseTarget(Outcome.AddAbility, target, source, game)) {
            return false;
        }

        if (game.getPermanent(target.getFirstTarget()) == null) {
            return false;
        }

        // Move Lucius to exile
        UUID exileId = CardUtil.getExileZoneId(game, source);
        String exileName = CardUtil.getSourceName(game, source);
        player.moveCardsToExile(game.getCard(source.getSourceId()), source, game, false, exileId, exileName);

        // Create effect to return him
        DelayedTriggeredAbility ability = new WhenTargetLeavesBattlefieldDelayedTriggeredAbility(
            new ReturnToBattlefieldUnderOwnerControlSourceEffect(), 
            Duration.Custom, 
            SetTargetPointer.CARD
        );
        ability.addTarget(target);
        game.addDelayedTriggeredAbility(ability, source);

        return true;
    }

    @Override
    public LuciusTheEternalEffect copy() {
        return new LuciusTheEternalEffect(this);
    }
}