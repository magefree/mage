package mage.cards.t;

import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.abilities.mana.conditional.ManaCondition;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;

/**
 * @author TheElk801
 */
public final class TitansNest extends CardImpl {

    public TitansNest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}{G}{U}");

        // At the beginning of your upkeep, look at the top card of your library. You may put that card into your graveyard.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new TitansNestEffect(), TargetController.YOU, false
        ));

        // Exile a card from your graveyard: Add {C}. Spend this mana only to cast a colored spell without {X} in its mana cost.
        this.addAbility(new TitansNestManaAbility());
    }

    private TitansNest(final TitansNest card) {
        super(card);
    }

    @Override
    public TitansNest copy() {
        return new TitansNest(this);
    }
}

class TitansNestEffect extends OneShotEffect {

    TitansNestEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top card of your library. You may put that card into your graveyard";
    }

    private TitansNestEffect(final TitansNestEffect effect) {
        super(effect);
    }

    @Override
    public TitansNestEffect copy() {
        return new TitansNestEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        player.lookAtCards("", card, game);
        return player.chooseUse(outcome, "Put " + card.getName() + " into your graveyard?", source, game)
                && player.moveCards(card, Zone.GRAVEYARD, source, game);
    }
}

class TitansNestManaAbility extends ActivatedManaAbilityImpl {

    TitansNestManaAbility() {
        super(Zone.BATTLEFIELD, (BasicManaEffect) new BasicManaEffect(
                new TitansNestConditionalMana(), new CardsInControllerGraveyardCount())
                .setText("Add {C}. Spend this mana only to cast a spell that's one or more colors without {X} in its mana cost."),
                new ExileFromGraveCost(new TargetCardInYourGraveyard()));
        this.netMana.add(Mana.ColorlessMana(1));
    }

    private TitansNestManaAbility(TitansNestManaAbility ability) {
        super(ability);
    }

    @Override
    public TitansNestManaAbility copy() {
        return new TitansNestManaAbility(this);
    }
}

class TitansNestConditionalMana extends ConditionalMana {

    TitansNestConditionalMana() {
        super(Mana.ColorlessMana(1));
        staticText = "Spend this mana only to cast a spell that's one or more colors without {X} in its mana cost.";
        addCondition(new TitansNestManaCondition());
    }
}

class TitansNestManaCondition extends ManaCondition {

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costToPay) {
        if (!(source instanceof SpellAbility)) {
            return false;
        }
        MageObject object = game.getObject(source.getSourceId());
        if (object == null || object.getColor(game).isColorless()) {
            return false;
        }
        if (costToPay instanceof ManaCosts) {
            return ((ManaCosts) costToPay).getVariableCosts().isEmpty();
        } else {
            return !(costToPay instanceof VariableManaCost);
        }
    }
}
