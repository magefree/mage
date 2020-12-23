package mage.game.command.emblems;

import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.filter.common.FilterOwnedCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author weirddan455
 */
public class KayaTheInexorableEmblem  extends Emblem {

    // −7: You get an emblem with "At the beginning of your upkeep, you may cast a legendary spell from your hand, from your graveyard, or from among cards you own in exile without paying its mana cost."
    public KayaTheInexorableEmblem() {

        this.setName("Emblem Kaya");
        this.setExpansionSetCodeForImage("KHM");
        this.getAbilities().add(new BeginningOfUpkeepTriggeredAbility(Zone.COMMAND, new KayaTheInexorableEmblemEffect(), TargetController.YOU, true, false));
    }
}

class KayaTheInexorableEmblemEffect extends OneShotEffect {

    private static final FilterOwnedCard filter = new FilterOwnedCard();
    private static final Set<String> choices = new LinkedHashSet<>();
    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
        choices.add("Hand");
        choices.add("Graveyard");
        choices.add("Exile");
    }

    public KayaTheInexorableEmblemEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "cast a legendary spell from your hand, from your graveyard, or from among cards you own in exile without paying its mana cost";
    }

    private KayaTheInexorableEmblemEffect(final KayaTheInexorableEmblemEffect effect) {
        super(effect);
    }

    @Override
    public KayaTheInexorableEmblemEffect copy() {
        return new KayaTheInexorableEmblemEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Choice zoneChoice = new ChoiceImpl(true);
            zoneChoice.setMessage("Cast a legendary spell from hand, graveyard, or exile");
            zoneChoice.setChoices(choices);
            zoneChoice.clearChoice();
            if (player.choose(Outcome.PlayForFree, zoneChoice, game)) {
                TargetCard target = null;
                switch (zoneChoice.getChoice()) {
                    case "Hand":
                        target = new TargetCardInHand(0, 1, filter);
                        target.setTargetName("legendary spell from your hand");
                        break;
                    case "Graveyard":
                        target = new TargetCardInYourGraveyard(0, 1, filter, true);
                        target.setTargetName("legendary spell from your graveyard");
                        break;
                    case "Exile":
                        target = new TargetCardInExile(0, 1, filter, null, true);
                        target.setNotTarget(true);
                        target.setTargetName("legendary spell you own in exile");
                        break;
                }
                if (target != null && player.chooseTarget(Outcome.PlayForFree, target, source, game)) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
                        boolean cardWasCast = player.cast(player.chooseAbilityForCast(card, game, true),
                                game, true, new ApprovingObject(source, game));
                        game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
                        return cardWasCast;
                    }
                }
            }
        }
        return false;
    }
}
