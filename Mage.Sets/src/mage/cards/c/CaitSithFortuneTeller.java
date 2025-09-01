package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CaitSithFortuneTeller extends CardImpl {

    public CaitSithFortuneTeller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.MOOGLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Lucky Slots -- At the beginning of combat on your turn, scry 1, then exile the top card of your library. You may play that card this turn. When you exile a card this way, target creature you control gets +X/+0 until end of turn, where X is that card's mana value.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new CaitSithFortuneTellerEffect()).withFlavorWord("Lucky Slots"));
    }

    private CaitSithFortuneTeller(final CaitSithFortuneTeller card) {
        super(card);
    }

    @Override
    public CaitSithFortuneTeller copy() {
        return new CaitSithFortuneTeller(this);
    }
}

class CaitSithFortuneTellerEffect extends OneShotEffect {

    CaitSithFortuneTellerEffect() {
        super(Outcome.Benefit);
        staticText = "scry 1, then exile the top card of your library. You may play that card this turn. " +
                "When you exile a card this way, target creature you control gets +X/+0 until end of turn, " +
                "where X is that card's mana value";
    }

    private CaitSithFortuneTellerEffect(final CaitSithFortuneTellerEffect effect) {
        super(effect);
    }

    @Override
    public CaitSithFortuneTellerEffect copy() {
        return new CaitSithFortuneTellerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.scry(1, source, game);
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return true;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        CardUtil.makeCardPlayable(game, source, card, false, Duration.EndOfTurn, false);
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new BoostTargetEffect(card.getManaValue(), 0), false
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
