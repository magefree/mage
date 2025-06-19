package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.AttacksPlayerWithCreaturesTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.DemonToken;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Arrays;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DemonicCovenant extends CardImpl {
    FilterPermanent filter = new FilterControlledPermanent(SubType.DEMON,"Demons you control");

    public DemonicCovenant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.KINDRED, CardType.ENCHANTMENT}, "{4}{B}{B}");

        this.subtype.add(SubType.DEMON);

        // Whenever one or more Demons you control attack a player, you draw a card and lose 1 life.
        Ability abilityAttack = new AttacksPlayerWithCreaturesTriggeredAbility(new DrawCardSourceControllerEffect(1, true), filter, SetTargetPointer.NONE);
        abilityAttack.addEffect(new LoseLifeSourceControllerEffect(1).setText("and lose 1 life"));
        this.addAbility(abilityAttack);

        // At the beginning of your end step, create a 5/5 black Demon creature token with flying, then mill two cards. If two cards that share all their card types were milled this way, sacrifice Demonic Covenant.
        Ability abilityEndStep = new BeginningOfEndStepTriggeredAbility(new CreateTokenEffect(new DemonToken()));
        abilityEndStep.addEffect(new DemonicCovenantEffect());
        this.addAbility(abilityEndStep);
    }

    private DemonicCovenant(final DemonicCovenant card) {
        super(card);
    }

    @Override
    public DemonicCovenant copy() {
        return new DemonicCovenant(this);
    }
}

class DemonicCovenantEffect extends OneShotEffect {

    DemonicCovenantEffect() {
        super(Outcome.Benefit);
        staticText = ", then mill two cards. If two cards that share " +
                "all their card types were milled this way, sacrifice {this}";
    }

    private DemonicCovenantEffect(final DemonicCovenantEffect effect) {
        super(effect);
    }

    @Override
    public DemonicCovenantEffect copy() {
        return new DemonicCovenantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = player.millCards(2, source, game);
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        return cards.size() >= 2
                && permanent != null
                && CardUtil
                .checkAnyPairs(
                        cards.getCards(game),
                        (c1, c2) -> Arrays
                                .stream(CardType.values())
                                .allMatch(cardType -> c1.getCardType(game).contains(cardType)
                                        == c2.getCardType(game).contains(cardType))
                )
                && permanent.sacrifice(source, game);
    }
}
