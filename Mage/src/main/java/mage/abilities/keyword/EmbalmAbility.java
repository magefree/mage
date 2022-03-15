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
 * @author LevelX2
 */
public class EmbalmAbility extends ActivatedAbilityImpl {

    private final String rule;

    public EmbalmAbility(Cost cost, Card card) {
        super(Zone.GRAVEYARD, new EmbalmEffect(), cost);
        addCost(new ExileSourceFromGraveCost());
        this.rule = setRule(cost, card);
        this.timing = TimingRule.SORCERY;
    }

    public EmbalmAbility(final EmbalmAbility ability) {
        super(ability);
        this.rule = ability.rule;
    }

    @Override
    public EmbalmAbility copy() {
        return new EmbalmAbility(this);
    }

    @Override
    public String getRule() {
        return rule;
    }

    private String setRule(Cost cost, Card card) {
        return "Embalm " + cost.getText() + " <i>(" + cost.getText() + ", Exile this card from your graveyard: " +
                "Create a token that's a copy of it, except it's a white Zombie " +
                card.getSubtype()
                        .stream()
                        .map(SubType::getDescription)
                        .map(s -> s + ' ')
                        .collect(Collectors.joining()) +
                "with no mana cost. Embalm only as a sorcery.)</i>";
    }
}

class EmbalmEffect extends OneShotEffect {

    public EmbalmEffect() {
        super(Outcome.PutCreatureInPlay);
    }

    public EmbalmEffect(final EmbalmEffect effect) {
        super(effect);
    }

    @Override
    public EmbalmEffect copy() {
        return new EmbalmEffect(this);
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
        token.getColor().setColor(ObjectColor.WHITE);
        token.addSubType(SubType.ZOMBIE);
        token.getManaCost().clear();
        game.fireEvent(GameEvent.getEvent(GameEvent.EventType.EMBALMED_CREATURE, token.getId(), source, controller.getId()));
        token.putOntoBattlefield(1, game, source, controller.getId(), false, false, null);
        // Probably it makes sense to remove also the Embalm ability (it's not shown on the token cards).
        // Also it can never get active or? But it's not mentioned in the reminder text.
        return true;
    }
}
