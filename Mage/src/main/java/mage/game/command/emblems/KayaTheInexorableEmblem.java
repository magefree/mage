package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterOwnedCard;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author weirddan455
 */
public class KayaTheInexorableEmblem extends Emblem {

    // −7: You get an emblem with "At the beginning of your upkeep, you may cast a legendary spell from your hand, from your graveyard, or from among cards you own in exile without paying its mana cost."
    public KayaTheInexorableEmblem() {

        super("Emblem Kaya");
        this.getAbilities().add(new BeginningOfUpkeepTriggeredAbility(
                Zone.COMMAND, new KayaTheInexorableEmblemEffect(),
                TargetController.YOU, true, false
        ));
    }

    private KayaTheInexorableEmblem(final KayaTheInexorableEmblem card) {
        super(card);
    }

    @Override
    public KayaTheInexorableEmblem copy() {
        return new KayaTheInexorableEmblem(this);
    }
}

class KayaTheInexorableEmblemEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterOwnedCard();
    private static final FilterCard filter2 = new FilterCard();
    private static final Set<String> choices = new LinkedHashSet<>();

    static {
        filter2.add(SuperType.LEGENDARY.getPredicate());
        choices.add("Hand");
        choices.add("Graveyard");
        choices.add("Exile");
    }

    public KayaTheInexorableEmblemEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "cast a legendary spell from your hand, from your graveyard, " +
                "or from among cards you own in exile without paying its mana cost";
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
        if (player == null) {
            return false;
        }
        Choice zoneChoice = new ChoiceImpl(true);
        zoneChoice.setMessage("Cast a legendary spell from hand, graveyard, or exile");
        zoneChoice.setChoices(choices);
        zoneChoice.clearChoice();
        player.choose(Outcome.PlayForFree, zoneChoice, game);
        Cards cards = new CardsImpl();
        switch (zoneChoice.getChoice()) {
            case "Hand":
                cards.addAll(player.getHand());
                break;
            case "Graveyard":
                cards.addAll(player.getGraveyard());
                break;
            case "Exile":
                cards.addAllCards(game.getExile().getCards(filter, game));
                break;
        }
        return CardUtil.castSpellWithAttributesForFree(player, source, game, cards, filter2);
    }
}
