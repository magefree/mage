package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SoulOfWindgrace extends CardImpl {

    public SoulOfWindgrace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Whenever Soul of Windgrace enters the battlefield or attacks, you may put a land card from a graveyard onto the battlefield tapped under your control.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new SoulOfWindgraceEffect()));

        // {G}, Discard a land card: You gain 3 life.
        Ability ability = new SimpleActivatedAbility(
                new GainLifeEffect(3), new ManaCostsImpl<>("{G}")
        );
        ability.addCost(new DiscardTargetCost(new TargetCardInHand(StaticFilters.FILTER_CARD_LAND_A)));
        this.addAbility(ability);

        // {1}{R}, Discard a land card: Draw a card.
        ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{1}{R}")
        );
        ability.addCost(new DiscardTargetCost(new TargetCardInHand(StaticFilters.FILTER_CARD_LAND_A)));
        this.addAbility(ability);

        // {2}{B}, Discard a land card: Soul of Windgrace gains indestructible until end of turn. Tap it.
        ability = new SimpleActivatedAbility(new GainAbilitySourceEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ), new ManaCostsImpl<>("{2}{B}"));
        ability.addEffect(new TapSourceEffect().setText("tap it"));
        ability.addCost(new DiscardTargetCost(new TargetCardInHand(StaticFilters.FILTER_CARD_LAND_A)));
        this.addAbility(ability);
    }

    private SoulOfWindgrace(final SoulOfWindgrace card) {
        super(card);
    }

    @Override
    public SoulOfWindgrace copy() {
        return new SoulOfWindgrace(this);
    }
}

class SoulOfWindgraceEffect extends OneShotEffect {

    SoulOfWindgraceEffect() {
        super(Outcome.PutLandInPlay);
        staticText = "you may put a land card from a graveyard onto the battlefield tapped under your control";
    }

    private SoulOfWindgraceEffect(final SoulOfWindgraceEffect effect) {
        super(effect);
    }

    @Override
    public SoulOfWindgraceEffect copy() {
        return new SoulOfWindgraceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCardInGraveyard target = new TargetCardInGraveyard(
                0, 1, StaticFilters.FILTER_CARD_LAND_A, true
        );
        player.choose(outcome, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        return card != null && player.moveCards(
                card, Zone.BATTLEFIELD, source, game, true, false, false, null
        );
    }
}
