package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.*;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.token.*;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.Targets;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author anonymous
 */
public final class LiberatedLivestock extends CardImpl {

    public LiberatedLivestock(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");
        
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.OX);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);



        // When Liberated Livestock dies, create a 1/1 white Cat creature token with lifelink,
        //this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new CatToken2())));
        // a 1/1 white Bird creature token with flying,
        //this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new BirdToken())));
        // and a 2/4 white Ox creature token.
        //this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new OxToken())));
        // For each of those tokens, you may put an Aura card from your hand and/or graveyard onto the battlefield attached to it.
        this.addAbility(new DiesSourceTriggeredAbility(new LiberatedLivestockEffect()));
    }

    private LiberatedLivestock(final LiberatedLivestock card) {
        super(card);
    }

    @Override
    public LiberatedLivestock copy() {
        return new LiberatedLivestock(this);
    }
}

//QUESTION: Unsure if oneshoteffect is correct here
class LiberatedLivestockEffect extends OneShotEffect {

    LiberatedLivestockEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "When Liberated Livestock dies, create a 1/1 white Cat creature token with lifelink, a 1/1 white Bird creature token with flying, and a 2/4 white Ox creature token. For each of those tokens, you may put an Aura card from your hand and/or graveyard onto the battlefield attached to it.";
    }

    private LiberatedLivestockEffect(final LiberatedLivestockEffect effect) {super(effect);}

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer((source.getControllerId()));
        if (controller == null){
            return false;
        }
        Token cat = new CatToken2();
        Token bird = new BirdToken();
        Token ox = new OxToken();
        cat.putOntoBattlefield(1, game, source, source.getControllerId());
        bird.putOntoBattlefield(1, game, source, source.getControllerId());
        ox.putOntoBattlefield(1, game, source, source.getControllerId());

        /*
        FilterCard filter = new FilterCard("an Aura");
        filter.add(Subtype.AURA.getPredicate());

        TargetCard target;

        Cards chooseableCards = new CardsImpl();
        Cards cardsInHand = controller.getHand();
        Cards cardsInGraveyard = controller.getGraveyard();
        TargetCard thand = new TargetCardInHand();
        TargetCard tgraveyard = new TargetCardInYourGraveyard();
        Targets targets = new Targets(thand, tgraveyard);
        TargetCard tcard = new TargetCardUnion(cardsInHand, cardsInGraveyard);

         */



        /*


        controller.choose(outcome, target, source, game);
        Cards cards = new CardsImpl(target.getTargets());
        if (cards.isEmpty()) {
            return false;
        }
        cards.getCards(game)
                .stream()
                .forEach(card -> game.getState().setValue("attachTo:" + card.getId(), permanent));
        player.moveCards(cards, Zone.BATTLEFIELD, source, game);
        for (UUID cardId : cards) {
            permanent.addAttachment(cardId, source, game);
        }
        return true;
        */



        return true;
    }

    @Override
    public LiberatedLivestockEffect copy() {
        return new LiberatedLivestockEffect(this);
    }
}