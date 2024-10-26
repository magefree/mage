package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetPerpetuallyEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;
import mage.target.targetpointer.FixedTarget;
import mage.util.RandomUtil;

import java.util.UUID;

public final class VeteranCharger extends CardImpl {

    public VeteranCharger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Veteran Charger enters the battlefield, choose a creature card in your hand. It perpetually gets +2/+2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new VeteranChargerEffect()));
    }

    private VeteranCharger(final VeteranCharger card) {
        super(card);
    }

    @Override
    public VeteranCharger copy() {
        return new VeteranCharger(this);
    }
}

class VeteranChargerEffect extends OneShotEffect {

    VeteranChargerEffect() {
        super(Outcome.AddAbility);
        this.staticText = "choose a creature card in your hand. It perpetually gets +2/+2.";
    }

    private VeteranChargerEffect(final VeteranChargerEffect effect) {
        super(effect);
    }

    @Override
    public VeteranChargerEffect copy() {
        return new VeteranChargerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (!controller.getHand().isEmpty()) {
                Card cardFromHand = null;
                if (controller.getHand().size() > 1) {
                    TargetCard target = new TargetCardInHand(new FilterCreatureCard());
                    if (controller.choose(Outcome.AddAbility, controller.getHand(), target, source, game)) {
                        cardFromHand = game.getCard(target.getFirstTarget());
                    }
                } else {
                    cardFromHand = RandomUtil.randomFromCollection(controller.getHand().getCards(new FilterCreatureCard(), game));
                }
                if (cardFromHand == null) {
                    return false;
                }
                game.addEffect(new BoostTargetPerpetuallyEffect(2, 2).setTargetPointer(new FixedTarget(cardFromHand, game)), source);

            }
            return true;
        }
        return false;
    }
}

