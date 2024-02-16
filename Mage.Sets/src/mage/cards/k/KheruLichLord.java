package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.replacement.LeaveBattlefieldExileTargetReplacementEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class KheruLichLord extends CardImpl {

    public KheruLichLord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{G}{U}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, you may pay {2}{B}. If you do, return a creature card at random from your graveyard to the battlefield. It gains flying, trample, and haste. Exile that card at the beginning of the next end step. If that card would leave the battlefield, exile it instead of putting it anywhere else.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD,
                new DoIfCostPaid(new KheruLichLordEffect(), new ManaCostsImpl<>("{2}{B}"), "Return creature card from your graveyard?"), TargetController.YOU, false));
    }

    private KheruLichLord(final KheruLichLord card) {
        super(card);
    }

    @Override
    public KheruLichLord copy() {
        return new KheruLichLord(this);
    }
}

class KheruLichLordEffect extends OneShotEffect {

    KheruLichLordEffect() {
        super(Outcome.Benefit);
        this.staticText = "return a creature card at random from your graveyard to the battlefield. It gains flying, trample, and haste. Exile that card at the beginning of the next end step. If that card would leave the battlefield, exile it instead of putting it anywhere else";
    }

    private KheruLichLordEffect(final KheruLichLordEffect effect) {
        super(effect);
    }

    @Override
    public KheruLichLordEffect copy() {
        return new KheruLichLordEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cards = new CardsImpl(controller.getGraveyard().getCards(StaticFilters.FILTER_CARD_CREATURE, source.getControllerId(), source, game));
            Card card = cards.getRandom(game);
            if (card != null) {
                controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                Permanent permanent = game.getPermanent(card.getId());
                if (permanent != null) {
                    FixedTarget fixedTarget = new FixedTarget(permanent, game);
                    ContinuousEffect effect = new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn);
                    effect.setTargetPointer(fixedTarget);
                    game.addEffect(effect, source);

                    effect = new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn);
                    effect.setTargetPointer(fixedTarget);
                    game.addEffect(effect, source);

                    effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
                    effect.setTargetPointer(fixedTarget);
                    game.addEffect(effect, source);

                    ExileTargetEffect exileEffect = new ExileTargetEffect();
                    exileEffect.setTargetPointer(fixedTarget);
                    DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect);
                    game.addDelayedTriggeredAbility(delayedAbility, source);

                    ReplacementEffect replacementEffect = new LeaveBattlefieldExileTargetReplacementEffect("that card");
                    replacementEffect.setTargetPointer(fixedTarget);
                    game.addEffect(replacementEffect, source);
                }
            }
            return true;
        }

        return false;
    }
}
