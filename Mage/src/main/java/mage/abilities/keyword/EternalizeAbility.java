
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

/**
 *
 * @author igoudt
 */
public class EternalizeAbility extends ActivatedAbilityImpl {

    private String rule;

    public EternalizeAbility(Cost cost, Card card) {
        super(Zone.GRAVEYARD, new EternalizeEffect(), cost);
        addCost(new ExileSourceFromGraveCost());
        this.rule = setRule(cost, card);
        this.timing = TimingRule.SORCERY;
        setRule(cost, card);
    }

    public EternalizeAbility(Cost cost, Card card, String rule) {
        this(cost, card);
        this.rule = rule;
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

    private String setRule(Cost cost, Card card) {
        StringBuilder sb = new StringBuilder("Eternalize ").append(cost.getText());
        sb.append(" <i>(").append(cost.getText());
        sb.append(", Exile this card from your graveyard: Create a token that's a copy of it, except it's a 4/4 black Zombie ");
        for (SubType subtype : card.getSubtype(null)) {
            sb.append(subtype).append(" ");
        }
        sb.append(" with no mana cost. Eternalize only as a sorcery.)</i>");
        return sb.toString();
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
        EmptyToken token = new EmptyToken();
        CardUtil.copyTo(token).from(card); // needed so that entersBattlefied triggered abilities see the attributes (e.g. Master Biomancer)
        token.getColor(game).setColor(ObjectColor.BLACK);
        if (!token.hasSubtype(SubType.ZOMBIE, game)) {
            token.getSubtype(game).add(0, SubType.ZOMBIE);
        }
        token.getManaCost().clear();
        token.removePTCDA();
        token.getPower().modifyBaseValue(4);
        token.getToughness().modifyBaseValue(4);
        game.fireEvent(GameEvent.getEvent(GameEvent.EventType.ETERNALIZED_CREATURE, token.getId(), source.getSourceId(), controller.getId()));
        token.putOntoBattlefield(1, game, source.getSourceId(), controller.getId(), false, false, null);
        // Probably it makes sense to remove also the Eternalize ability (it's not shown on the token cards).
        // Also it can never get active or? But it's not mentioned in the reminder text.
        return true;
    }
}
