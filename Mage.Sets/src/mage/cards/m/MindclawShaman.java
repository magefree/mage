
package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class MindclawShaman extends CardImpl {

    public MindclawShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");
        this.subtype.add(SubType.VIASHINO);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Mindclaw Shaman enters the battlefield, target opponent reveals their hand. 
        // You may cast an instant or sorcery card from it without paying its mana cost.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MindclawShamanEffect(), false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private MindclawShaman(final MindclawShaman card) {
        super(card);
    }

    @Override
    public MindclawShaman copy() {
        return new MindclawShaman(this);
    }
}

class MindclawShamanEffect extends OneShotEffect {

    public MindclawShamanEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "target opponent reveals their hand. You may cast "
                + "an instant or sorcery card from it without paying its mana cost";
    }

    public MindclawShamanEffect(final MindclawShamanEffect effect) {
        super(effect);
    }

    @Override
    public MindclawShamanEffect copy() {
        return new MindclawShamanEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (controller == null || opponent == null) {
            return false;
        }
        opponent.revealCards(source, opponent.getHand(), game);
        return CardUtil.castSpellWithAttributesForFree(
                controller, source, game, new CardsImpl(opponent.getHand()),
                StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY
        );
    }
}
