package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ExploreSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.functions.EmptyCopyApplier;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class DeepfathomEcho extends CardImpl {

    public DeepfathomEcho(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // At the beginning of combat on your turn, Deepfathom Echo explores. Then you may have it become a copy of another creature you control until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new ExploreSourceEffect(false, "{this}"), TargetController.YOU, false
        );
        ability.addEffect(new DeepfathomEchoEffect());
        this.addAbility(ability);
    }

    private DeepfathomEcho(final DeepfathomEcho card) {
        super(card);
    }

    @Override
    public DeepfathomEcho copy() {
        return new DeepfathomEcho(this);
    }
}

class DeepfathomEchoEffect extends OneShotEffect {

    public DeepfathomEchoEffect() {
        super(Outcome.Copy);
        this.staticText = "Then you may have it become a copy of another creature you control until end of turn";
    }

    private DeepfathomEchoEffect(final DeepfathomEchoEffect effect) {
        super(effect);
    }

    @Override
    public DeepfathomEchoEffect copy() {
        return new DeepfathomEchoEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent deepfathom = source.getSourcePermanentIfItStillExists(game);
        Player controller = game.getPlayer(source.getControllerId());
        if (deepfathom == null || controller == null) {
            return false;
        }

        TargetPermanent target = new TargetPermanent(
                0, 1, StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL, true
        );
        controller.chooseTarget(outcome, target, source, game);
        Permanent chosenCreature = game.getPermanent(target.getFirstTarget());
        if (chosenCreature == null) {
            return false;
        }

        game.copyPermanent(Duration.EndOfTurn, chosenCreature, deepfathom.getId(), source, new EmptyCopyApplier());
        return true;
    }
}
