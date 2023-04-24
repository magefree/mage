package mage.cards.c;

import mage.ApprovingObject;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.UUID;

public class CapriciousHellraiser extends CardImpl {
    public CapriciousHellraiser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}{R}");
        this.addSubType(SubType.PHYREXIAN);
        this.addSubType(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        //This spell costs {3} less to cast if you have nine or more cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SpellCostReductionSourceEffect(3,
                new CardsInControllerGraveyardCondition(9)
        )));

        //Flying
        this.addAbility(FlyingAbility.getInstance());

        //When Capricious Hellraiser enters the battlefield, exile three cards at random from your graveyard. Choose a
        //noncreature, nonland card from among them and copy it. You may cast the copy without paying its mana cost.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CapriciousHellraiserEffect()));
    }

    private CapriciousHellraiser(final CapriciousHellraiser card) {
        super(card);
    }

    @Override
    public CapriciousHellraiser copy() {
        return new CapriciousHellraiser(this);
    }
}

class CapriciousHellraiserEffect extends OneShotEffect {

    public CapriciousHellraiserEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile three cards at random from your graveyard. Choose a noncreature, nonland card from " +
                "among them and copy it. You may cast the copy without paying its mana cost";
    }

    private CapriciousHellraiserEffect(final CapriciousHellraiserEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cardsInGraveyard = new CardsImpl(controller.getGraveyard().getCards(game));
            Cards toExile = new CardsImpl();
            Cards cardsToChooseFrom = new CardsImpl();
            for (int i = 0; i < 3; i++) {
                Card card = cardsInGraveyard.getRandom(game);
                if (card != null) {
                    toExile.add(card);
                    cardsInGraveyard.remove(card);
                    if (!card.isCreature() && !card.isLand()) {
                        cardsToChooseFrom.add(card);
                    }
                }
            }
            controller.moveCards(toExile, Zone.EXILED, source, game);
            if (cardsToChooseFrom.size() > 0) {
                TargetCard targetCard = new TargetCard(1, Zone.EXILED, StaticFilters.FILTER_CARD);
                controller.choose(Outcome.Copy, cardsToChooseFrom, targetCard, source, game);
                Card cardToCopy = game.getCard(targetCard.getTargets().get(0));
                if (cardToCopy == null) {
                    return true;
                }
                if (!controller.chooseUse(outcome, "Cast copy of " + cardToCopy.getName() + " without paying its mana cost?", source, game)) {
                    return true;
                }
                Card copiedCard = game.copyCard(cardToCopy, source, controller.getId());
                game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), Boolean.TRUE);
                controller.cast(
                        controller.chooseAbilityForCast(copiedCard, game, true),
                        game, true, new ApprovingObject(source, game)
                );
                game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), null);
            }
            return true;
        }
        return false;
    }

    @Override
    public CapriciousHellraiserEffect copy() {
        return new CapriciousHellraiserEffect(this);
    }
}
