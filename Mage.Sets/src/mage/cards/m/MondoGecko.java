package mage.cards.m;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.ColorsAmongControlledPermanentsCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BecomesColorSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HexproofBaseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MondoGecko extends CardImpl {

    public MondoGecko(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {1}, Discard a card: Until end of turn, Mondo Gecko becomes the color of your choice and gains hexproof from that color.
        Ability ability = new SimpleActivatedAbility(new MondoGeckoEffect(), new GenericManaCost(1));
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);

        // Whenever Mondo Gecko deals combat damage to a player, draw a card for each color among permanents you control.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DrawCardSourceControllerEffect(ColorsAmongControlledPermanentsCount.ALL_PERMANENTS)
        ).addHint(ColorsAmongControlledPermanentsCount.ALL_PERMANENTS.getHint()));
    }

    private MondoGecko(final MondoGecko card) {
        super(card);
    }

    @Override
    public MondoGecko copy() {
        return new MondoGecko(this);
    }
}

class MondoGeckoEffect extends OneShotEffect {

    MondoGeckoEffect() {
        super(Outcome.Benefit);
        staticText = "until end of turn, {this} becomes the color of your choice and gains hexproof from that color";
    }

    private MondoGeckoEffect(final MondoGeckoEffect effect) {
        super(effect);
    }

    @Override
    public MondoGeckoEffect copy() {
        return new MondoGeckoEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        ChoiceColor choice = new ChoiceColor(true);
        player.choose(outcome, choice, game);
        ObjectColor color = choice.getColor();
        game.addEffect(new BecomesColorSourceEffect(color, Duration.EndOfTurn), source);
        game.addEffect(new GainAbilitySourceEffect(HexproofBaseAbility.getFirstFromColor(color)), source);
        return true;
    }
}
