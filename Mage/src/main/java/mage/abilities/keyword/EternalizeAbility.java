package mage.abilities.keyword;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.EmptyToken;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.stream.Collectors;

/**
 * @author igoudt
 */
public class EternalizeAbility extends ActivatedAbilityImpl {

    private final String rule;

    public EternalizeAbility(Cost cost, Card card) {
        this(cost, card, setRule(cost, card));
    }

    public EternalizeAbility(Cost cost, Card card, String rule) {
        super(Zone.GRAVEYARD, new EternalizeEffect(), cost);
        addCost(new ExileSourceFromGraveCost());
        this.rule = rule;
        this.timing = TimingRule.SORCERY;
    }

    public EternalizeAbility(final EternalizeAbility ability) {
        super(ability);
        this.rule = ability.rule;
    }

    @Override
    public EternalizeAbility copy() {
        return new EternalizeAbility(this);
    }

    @Override
    public String getRule() {
        return rule;
    }

    private static String setRule(Cost cost, Card card) {
        return "Eternalize " + cost.getText() + " <i>(" + cost.getText() + ", Exile this card from your graveyard: " +
                "Create a token that's a copy of it, except it's a 4/4 black Zombie " +
                card.getSubtype()
                        .stream()
                        .map(SubType::getDescription)
                        .map(s -> s + ' ')
                        .collect(Collectors.joining()) +
                "with no mana cost. Eternalize only as a sorcery.)</i>";
    }
}

class EternalizeEffect extends OneShotEffect {

    public EternalizeEffect() {
        super(Outcome.PutCreatureInPlay);
    }

    public EternalizeEffect(final EternalizeEffect effect) {
        super(effect);
    }

    @Override
    public EternalizeEffect copy() {
        return new EternalizeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getSourceId());
        if (card == null) {
            return false;
        }
        Player controller = game.getPlayer(card.getOwnerId());
        if (controller == null) {
            return false;
        }

        // create token and modify all attributes permanently (without game usage)
        EmptyToken token = new EmptyToken();
        CardUtil.copyTo(token).from(card, game); // needed so that entersBattlefied triggered abilities see the attributes (e.g. Master Biomancer)
        token.getColor().setColor(ObjectColor.BLACK);
        token.addSubType(SubType.ZOMBIE);
        token.getManaCost().clear();
        token.removePTCDA();
        token.getPower().modifyBaseValue(4);
        token.getToughness().modifyBaseValue(4);
        game.fireEvent(GameEvent.getEvent(GameEvent.EventType.ETERNALIZED_CREATURE, token.getId(), source, controller.getId()));
        token.putOntoBattlefield(1, game, source, controller.getId(), false, false, null);
        // Probably it makes sense to remove also the Eternalize ability (it's not shown on the token cards).
        // Also it can never get active or? But it's not mentioned in the reminder text.
        return true;
    }
}
