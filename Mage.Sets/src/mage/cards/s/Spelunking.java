package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.EnterUntappedAllEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class Spelunking extends CardImpl {

    public Spelunking(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // When Spelunking enters the battlefield, draw a card, then you may put a land card from your hand onto the battlefield. If you put a Cave onto the battlefield this way, you gain 4 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1));
        ability.addEffect(new SpelunkingEffect());
        this.addAbility(ability);

        // Lands you control enter the battlefield untapped.
        this.addAbility(new SimpleStaticAbility(new EnterUntappedAllEffect(StaticFilters.FILTER_CONTROLLED_PERMANENT_LANDS)));
    }

    private Spelunking(final Spelunking card) {
        super(card);
    }

    @Override
    public Spelunking copy() {
        return new Spelunking(this);
    }
}

class SpelunkingEffect extends OneShotEffect {

    SpelunkingEffect() {
        super(Outcome.Benefit);
        staticText = ", then you may put a land card from your hand onto the battlefield. "
                + "If you put a Cave onto the battlefield this way, you gain 4 life";
    }

    private SpelunkingEffect(final SpelunkingEffect effect) {
        super(effect);
    }

    @Override
    public SpelunkingEffect copy() {
        return new SpelunkingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetCardInHand target = new TargetCardInHand(0, 1, StaticFilters.FILTER_CARD_LAND);
        controller.choose(Outcome.PutLandInPlay, target, source, game);
        Card landInHand = game.getCard(target.getFirstTarget());
        if (landInHand == null) {
            return false;
        }
        controller.moveCards(landInHand, Zone.BATTLEFIELD, source, game);
        if (landInHand.hasSubtype(SubType.CAVE, game)) {
            controller.gainLife(4, game, source);
        }
        return true;
    }
}
