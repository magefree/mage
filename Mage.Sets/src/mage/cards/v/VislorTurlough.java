package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.DoctorsCompanionAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VislorTurlough extends CardImpl {

    public VislorTurlough(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Deal with the Black Guardian -- When Vislor Turlough enters the battlefield, you may have an opponent gain control of it. If you do, it's goaded for as long as they control it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new VislorTurloughEffect(), true)
                .withFlavorWord("Deal with the Black Guardian"));

        // At the beginning of your end step, draw a card, then you lose life equal to the number of cards in your hand.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new DrawCardSourceControllerEffect(1));
        ability.addEffect(new LoseLifeSourceControllerEffect(CardsInControllerHandCount.ANY)
                .setText(", then you lose life equal to the number of cards in your hand"));
        this.addAbility(ability);

        // Doctor's companion
        this.addAbility(DoctorsCompanionAbility.getInstance());
    }

    private VislorTurlough(final VislorTurlough card) {
        super(card);
    }

    @Override
    public VislorTurlough copy() {
        return new VislorTurlough(this);
    }
}

class VislorTurloughEffect extends OneShotEffect {

    VislorTurloughEffect() {
        super(Outcome.Benefit);
        staticText = "have an opponent gain control of it. If you do, it's goaded for as long as they control it";
    }

    private VislorTurloughEffect(final VislorTurloughEffect effect) {
        super(effect);
    }

    @Override
    public VislorTurloughEffect copy() {
        return new VislorTurloughEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (player == null || permanent == null) {
            return false;
        }
        TargetPlayer target = new TargetOpponent(0, 1, true);
        player.choose(Outcome.GainControl, target, source, game);
        Player opponent = game.getPlayer(target.getFirstTarget());
        if (opponent == null) {
            return false;
        }
        game.addEffect(new GainControlTargetEffect(
                Duration.Custom, true, opponent.getId()
        ).setTargetPointer(new FixedTarget(permanent, game)), source);
        game.addEffect(new VislorTurloughGoadEffect(opponent.getId(), permanent, game), source);
        return true;
    }
}

class VislorTurloughGoadEffect extends GoadTargetEffect {

    private final UUID controllerId;

    VislorTurloughGoadEffect(UUID controllerId, Permanent permanent, Game game) {
        super(Duration.Custom);
        this.controllerId = controllerId;
        this.setTargetPointer(new FixedTarget(permanent, game));
    }

    private VislorTurloughGoadEffect(final VislorTurloughGoadEffect effect) {
        super(effect);
        this.controllerId = effect.controllerId;
    }

    @Override
    public VislorTurloughGoadEffect copy() {
        return new VislorTurloughGoadEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null && permanent.isControlledBy(controllerId)) {
            return super.apply(game, source);
        }
        discard();
        return false;
    }
}
