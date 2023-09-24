package mage.cards.l;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.*;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Codermann63
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
        // a 1/1 white Bird creature token with flying,
        // and a 2/4 white Ox creature token.
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

class LiberatedLivestockEffect extends OneShotEffect {

    LiberatedLivestockEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "create a 1/1 white Cat creature token with lifelink, a 1/1 white Bird creature token with flying, and a 2/4 white Ox creature token. For each of those tokens, you may put an Aura card from your hand and/or graveyard onto the battlefield attached to it.";
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
        List<Token> tokens = Arrays.asList(cat, bird, ox);

        // pause to get token onto the battlefield all the same time
        game.getState().pause();
        tokens.forEach(token -> token.putOntoBattlefield(1, game, source, source.getControllerId()));
        game.getState().resume();

        // Setup for choosing and attaching auras to tokens
        FilterCard filter = new FilterCard("Up to one Aura");
        filter.add(SubType.AURA.getPredicate());
        TargetCard target;


        for (Token token : tokens){
            for (UUID t : token.getLastAddedTokenIds()){
                Permanent sourcePermanent = game.getPermanent(t);
                if (sourcePermanent == null){
                    continue;
                }
                filter.setMessage("Aura from you hand or graveyard that can attach to " + sourcePermanent.getName());

                // Choose from hand or graveyard?
                if (controller.chooseUse(outcome, "Look in Hand or Graveyard for aura to attach to "+sourcePermanent.getName()+"?", null, "Hand", "Graveyard", source, game)){
                    target = new TargetCardInHand(0,1,filter);}
                else{
                    target = new TargetCardInYourGraveyard(0,1,filter);}
                target.withNotTarget(true);

                // Handle putting aura onto the battlefield and attachment to token
                controller.chooseTarget(outcome, target, source, game);
                Card card = game.getCard(target.getFirstTarget());
                if (card == null ||
                        sourcePermanent.cantBeAttachedBy(card,source,game,true)) {
                    continue;
                }
                game.getState().setValue("attachTo:" + card.getId(), sourcePermanent);
                controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                sourcePermanent.addAttachment(card.getId(), source, game);
            }
        }
        return true;
    }

    @Override
    public LiberatedLivestockEffect copy() {
        return new LiberatedLivestockEffect(this);
    }
}