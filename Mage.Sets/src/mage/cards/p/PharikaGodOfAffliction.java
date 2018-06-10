
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.LoseCreatureTypeSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.token.PharikaSnakeToken;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author LevelX2
 */
public final class PharikaGodOfAffliction extends CardImpl {

    public PharikaGodOfAffliction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{B}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());
        // As long as your devotion to black and green is less than seven, Pharika isn't a creature.
        Effect effect = new LoseCreatureTypeSourceEffect(new DevotionCount(ColoredManaSymbol.B, ColoredManaSymbol.G), 7);
        effect.setText("As long as your devotion to black and green is less than seven, Pharika isn't a creature");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
        // {B}{G}: Exile target creature card from a graveyard. It's owner creates a 1/1 black and green Snake enchantment creature token with deathtouch.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PharikaExileEffect(), new ManaCostsImpl("{B}{G}"));
        Target target = new TargetCardInGraveyard(new FilterCreatureCard("a creature card from a graveyard"));
        ability.addTarget(target);
        this.addAbility(ability);

    }

    public PharikaGodOfAffliction(final PharikaGodOfAffliction card) {
        super(card);
    }

    @Override
    public PharikaGodOfAffliction copy() {
        return new PharikaGodOfAffliction(this);
    }
}

class PharikaExileEffect extends OneShotEffect {

    public PharikaExileEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Exile target creature card from a graveyard. Its owner creates a 1/1 black and green Snake enchantment creature token with deathtouch";
    }

    public PharikaExileEffect(final PharikaExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card targetCard = game.getCard(source.getFirstTarget());
            if (targetCard != null) {
                if (game.getState().getZone(source.getFirstTarget()) == Zone.GRAVEYARD) {
                    controller.moveCardToExileWithInfo(targetCard, null, "", source.getSourceId(), game, Zone.GRAVEYARD, true);
                }
                Player tokenController = game.getPlayer(targetCard.getOwnerId());
                if (tokenController != null) {
                    return new PharikaSnakeToken().putOntoBattlefield(1, game, source.getSourceId(), tokenController.getId());
                }
            }
        }
        return false;
    }

    @Override
    public PharikaExileEffect copy() {
        return new PharikaExileEffect(this);
    }

}
