package mage.cards.s;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.NinjutsuAbility;
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

/**
 * @author LevelX2
 */
public final class SilentBladeOni extends CardImpl {

    public SilentBladeOni(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}{B}{B}");
        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.NINJA);

        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Ninjutsu {4}{U}{B}
        this.addAbility(new NinjutsuAbility(new ManaCostsImpl("{4}{U}{B}")));

        // Whenever Silent-Blade Oni deals combat damage to a player, look at that player's hand. You may cast a nonland card in it without paying that card's mana cost.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new SilentBladeOniEffect(), false, true
        ));
    }

    private SilentBladeOni(final SilentBladeOni card) {
        super(card);
    }

    @Override
    public SilentBladeOni copy() {
        return new SilentBladeOni(this);
    }
}

class SilentBladeOniEffect extends OneShotEffect {

    SilentBladeOniEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "look at that player's hand. " +
                "You may cast a nonland card in it without paying that card's mana cost";
    }

    private SilentBladeOniEffect(final SilentBladeOniEffect effect) {
        super(effect);
    }

    @Override
    public SilentBladeOniEffect copy() {
        return new SilentBladeOniEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (opponent == null || controller == null) {
            return false;
        }
        Cards cardsInHand = new CardsImpl();
        cardsInHand.addAll(opponent.getHand());
        if (cardsInHand.isEmpty()) {
            return true;
        }
        TargetCard target = new TargetCard(
                0, 1, Zone.HAND, StaticFilters.FILTER_CARD_A_NON_LAND
        );
        if (!controller.chooseUse(outcome, "Cast a card from " + opponent.getName() + "'s hand?", source, game)
                || !controller.chooseTarget(outcome, cardsInHand, target, source, game)) {
            return true;
        }
        Card card = game.getCard(target.getFirstTarget());
        return card != null && controller.cast(
                card.getSpellAbility(), game, true, new MageObjectReference(source.getSourceObject(game), game)
        );
    }
}
