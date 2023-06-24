package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheMouthOfSauron extends CardImpl {

    public TheMouthOfSauron(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When The Mouth of Sauron enters the battlefield, target player mills three cards. Then amass Orcs X, where X is the number of instant and sorcery cards in that player's graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MillCardsTargetEffect(3));
        ability.addEffect(new TheMouthOfSauronEffect());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private TheMouthOfSauron(final TheMouthOfSauron card) {
        super(card);
    }

    @Override
    public TheMouthOfSauron copy() {
        return new TheMouthOfSauron(this);
    }
}

class TheMouthOfSauronEffect extends OneShotEffect {

    TheMouthOfSauronEffect() {
        super(Outcome.Benefit);
        staticText = "Then amass Orcs X, where X is the number of instant and sorcery cards in that player's graveyard";
    }

    private TheMouthOfSauronEffect(final TheMouthOfSauronEffect effect) {
        super(effect);
    }

    @Override
    public TheMouthOfSauronEffect copy() {
        return new TheMouthOfSauronEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        return player != null && AmassEffect.doAmass(
                player.getGraveyard()
                        .count(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY, game),
                SubType.ORC, game, source
        ) != null;
    }
}
