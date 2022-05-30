package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.BoostOpponentsEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.WhiteBlackSpiritToken;
import mage.players.Player;
import mage.target.common.TargetCardInOpponentsGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EtherealAbsolution extends CardImpl {

    public EtherealAbsolution(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{W}{B}");

        // Creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(
                new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield)
        ));

        // Creatures your opponents control get -1/-1.
        this.addAbility(new SimpleStaticAbility(
                new BoostOpponentsEffect(-1, -1, Duration.WhileOnBattlefield)
        ));

        // {2}{W}{B}: Exile target card from an opponent's graveyard. If it was a creature card, you create a 1/1 white and black Spirit creature token with flying.
        Ability ability = new SimpleActivatedAbility(
                new EtherealAbsolutionEffect(), new ManaCostsImpl<>("{2}{W}{B}")
        );
        ability.addTarget(new TargetCardInOpponentsGraveyard(StaticFilters.FILTER_CARD));
        this.addAbility(ability);
    }

    private EtherealAbsolution(final EtherealAbsolution card) {
        super(card);
    }

    @Override
    public EtherealAbsolution copy() {
        return new EtherealAbsolution(this);
    }
}

class EtherealAbsolutionEffect extends OneShotEffect {

    EtherealAbsolutionEffect() {
        super(Outcome.Benefit);
        staticText = "Exile target card from an opponent's graveyard. " +
                "If it was a creature card, you create a 1/1 white and black Spirit creature token with flying.";
    }

    private EtherealAbsolutionEffect(final EtherealAbsolutionEffect effect) {
        super(effect);
    }

    @Override
    public EtherealAbsolutionEffect copy() {
        return new EtherealAbsolutionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getFirstTarget());
        if (player == null || card == null) {
            return false;
        }
        if (card.isCreature(game)) {
            new CreateTokenEffect(new WhiteBlackSpiritToken()).apply(game, source);
        }
        return player.moveCards(card, Zone.EXILED, source, game);
    }
}