package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

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
        this.addAbility(new NinjutsuAbility("{4}{U}{B}"));

        // Whenever Silent-Blade Oni deals combat damage to a player, look at that player's hand. 
        // You may cast a nonland card in it without paying that card's mana cost.
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
        this.staticText = "look at that player's hand. You may cast a spell " +
                "from among those cards without paying its mana cost";
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
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || opponent == null) {
            return false;
        }
        controller.lookAtCards(opponent.getName(), opponent.getHand(), game);
        return CardUtil.castSpellWithAttributesForFree(
                controller, source, game, new CardsImpl(opponent.getHand()),
                StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY
        );
    }
}
