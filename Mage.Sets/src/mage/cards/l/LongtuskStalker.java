package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.BoostSourcePerpetuallyEffect;
import mage.abilities.effects.common.continuous.BoostTargetPerpetuallyEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;
import mage.target.targetpointer.FixedTarget;
import mage.util.RandomUtil;

/**
 *
 * @author karapuzz
 */
public final class LongtuskStalker extends CardImpl {

    public LongtuskStalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Longtusk Stalker enters the battlefield or attacks, you get {E}.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new GetEnergyCountersControllerEffect(1), false));

        // Pay {E}{E}: Longtusk Stalker perpetually gets +1/+0. You may choose a creature card in your hand. If you do, that card perpetually gets +1/+0.
        this.addAbility(new SimpleActivatedAbility(
                new LogtuskAbilityEffect(), new PayEnergyCost(2)
        ));
    }

    private LongtuskStalker(final LongtuskStalker card) {
        super(card);
    }

    @Override
    public LongtuskStalker copy() {
        return new LongtuskStalker(this);
    }
}

class LogtuskAbilityEffect extends OneShotEffect {

    LogtuskAbilityEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "{this} perpetually gets +1/+0. You may choose a creature card in your hand. If you do, that card perpetually gets +1/+0.";
    }

    private LogtuskAbilityEffect(final LogtuskAbilityEffect effect) {
        super(effect);
    }

    @Override
    public LogtuskAbilityEffect copy() {
        return new LogtuskAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Effect boostLongtusk = new BoostSourcePerpetuallyEffect(1, 0);
            boostLongtusk.apply(game, source);

            if (!controller.getHand().isEmpty()) {
                Card cardFromHand = null;
                if (controller.getHand().size() > 1) {
                    TargetCard target = new TargetCardInHand(0, 1, new FilterCreatureCard());
                    if (controller.choose(Outcome.BoostCreature, controller.getHand(), target, source, game)) {
                        cardFromHand = game.getCard(target.getFirstTarget());
                    }
                } else {
                    cardFromHand = RandomUtil.randomFromCollection(controller.getHand().getCards(new FilterCreatureCard(), game));
                }

                if (cardFromHand == null) {
                    return false;
                }

                game.addEffect(new BoostTargetPerpetuallyEffect(1, 0)
                        .setTargetPointer(new FixedTarget(cardFromHand, game)), source);
                return true;
            }
        }
        return false;
    }

}

