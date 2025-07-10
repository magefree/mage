package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.WarpAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PossibilityTechnician extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.KAVU, "Kavu");

    public PossibilityTechnician(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.KAVU);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever this creature or another Kavu you control enters, exile the top card of your library. For as long as that card remains exiled, you may play it if you control a Kavu.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new PossibilityTechnicianEffect(), filter, false, true
        ));

        // Warp {1}{R}
        this.addAbility(new WarpAbility(this, "{1}{R}"));
    }

    private PossibilityTechnician(final PossibilityTechnician card) {
        super(card);
    }

    @Override
    public PossibilityTechnician copy() {
        return new PossibilityTechnician(this);
    }
}

class PossibilityTechnicianEffect extends OneShotEffect {

    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(new FilterControlledPermanent(SubType.KAVU));

    PossibilityTechnicianEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top card of your library. " +
                "For as long as that card remains exiled, you may play it if you control a Kavu";
    }

    private PossibilityTechnicianEffect(final PossibilityTechnicianEffect effect) {
        super(effect);
    }

    @Override
    public PossibilityTechnicianEffect copy() {
        return new PossibilityTechnicianEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        CardUtil.makeCardPlayable(
                game, source, card, false, Duration.Custom,
                false, source.getControllerId(), condition
        );
        return true;
    }
}
