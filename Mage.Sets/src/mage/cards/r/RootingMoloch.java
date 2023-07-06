package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RootingMoloch extends CardImpl {

    private static final FilterCard filter = new FilterCard("card with a cycling ability from your graveyard");

    static {
        filter.add(new AbilityPredicate(CyclingAbility.class));
    }

    public RootingMoloch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Rooting Moloch enters the battlefield, exile target card with a cycling ability from your graveyard. Until the end of your next turn, you may play that card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new RootingMolochEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private RootingMoloch(final RootingMoloch card) {
        super(card);
    }

    @Override
    public RootingMoloch copy() {
        return new RootingMoloch(this);
    }
}

class RootingMolochEffect extends OneShotEffect {

    RootingMolochEffect() {
        super(Outcome.Benefit);
        staticText = "exile target card with a cycling ability from your graveyard. " +
                "Until the end of your next turn, you may play that card.";
    }

    private RootingMolochEffect(final RootingMolochEffect effect) {
        super(effect);
    }

    @Override
    public RootingMolochEffect copy() {
        return new RootingMolochEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getFirstTarget());
        if (controller == null || card == null) {
            return false;
        }
        controller.moveCards(card, Zone.EXILED, source, game);
        CardUtil.makeCardPlayable(game, source, card, Duration.UntilEndOfYourNextTurn, null);
        return true;
    }
}

