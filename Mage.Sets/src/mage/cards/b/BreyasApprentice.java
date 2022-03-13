package mage.cards.b;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.ThopterColorlessToken;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BreyasApprentice extends CardImpl {

    public BreyasApprentice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Breya's Apprentice enters the battlefield, create a 1/1 colorless Thopter artifact creature token with flying.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new ThopterColorlessToken())));

        // {T}, Sacrifice an artifact: Choose one —
        // • Exile the top card of your library. Until the end of your next turn, you may play that card.
        Ability ability = new SimpleActivatedAbility(new BreyasApprenticeEffect(), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_AN
        )));

        // • Target creature gets +2/+0 until end of turn.
        Mode mode = new Mode(new BoostTargetEffect(2, 0, Duration.EndOfTurn));
        mode.addTarget(new TargetCreaturePermanent());
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private BreyasApprentice(final BreyasApprentice card) {
        super(card);
    }

    @Override
    public BreyasApprentice copy() {
        return new BreyasApprentice(this);
    }
}

class BreyasApprenticeEffect extends OneShotEffect {

    BreyasApprenticeEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top card of your library. Until the end of your next turn, you may play that card";
    }

    private BreyasApprenticeEffect(final BreyasApprenticeEffect effect) {
        super(effect);
    }

    @Override
    public BreyasApprenticeEffect copy() {
        return new BreyasApprenticeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller == null || sourceObject == null) {
            return false;
        }
        Card card = controller.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        controller.moveCardsToExile(card, source, game, true, source.getSourceId(), sourceObject.getIdName());
        game.addEffect(new PlayFromNotOwnHandZoneTargetEffect(
                Zone.EXILED, Duration.UntilEndOfYourNextTurn
        ).setTargetPointer(new FixedTarget(card, game)), source);
        return true;
    }
}
