
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.PersistAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInOpponentsGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class PuppeteerClique extends CardImpl {

    public PuppeteerClique(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Puppeteer Clique enters the battlefield, put target creature card from an opponent's graveyard onto the battlefield under your control. It gains haste. At the beginning of your next end step, exile it.
        Ability ability = new EntersBattlefieldTriggeredAbility(new PuppeteerCliqueEffect(), false);
        Target target = new TargetCardInOpponentsGraveyard(new FilterCreatureCard("creature card from your opponent's graveyard"));
        ability.addTarget(target);
        this.addAbility(ability);

        // Persist
        this.addAbility(new PersistAbility());
    }

    public PuppeteerClique(final PuppeteerClique card) {
        super(card);
    }

    @Override
    public PuppeteerClique copy() {
        return new PuppeteerClique(this);
    }
}

class PuppeteerCliqueEffect extends OneShotEffect {

    public PuppeteerCliqueEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "put target creature card from an opponent's graveyard onto the battlefield under your control. It gains haste. At the beginning of your next end step, exile it";
    }

    public PuppeteerCliqueEffect(final PuppeteerCliqueEffect effect) {
        super(effect);
    }

    @Override
    public PuppeteerCliqueEffect copy() {
        return new PuppeteerCliqueEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card != null) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                if (controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
                    Permanent permanent = game.getPermanent(card.getId());
                    if (permanent != null) {
                        ContinuousEffect hasteEffect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.Custom);
                        hasteEffect.setTargetPointer(new FixedTarget(permanent, game));
                        game.addEffect(hasteEffect, source);
                        ExileTargetEffect exileEffect = new ExileTargetEffect("exile " + permanent.getLogName());
                        exileEffect.setTargetPointer(new FixedTarget(permanent, game));
                        DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect, TargetController.YOU);
                        game.addDelayedTriggeredAbility(delayedAbility, source);
                        result = true;
                    }
                }
            }
        }
        return result;
    }
}
