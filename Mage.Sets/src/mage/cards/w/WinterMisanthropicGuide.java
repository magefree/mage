package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WinterMisanthropicGuide extends CardImpl {

    public WinterMisanthropicGuide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}"), false));

        // At the beginning of your upkeep, each player draws two cards.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new DrawCardAllEffect(2)
        ));

        // Delirium -- As long as there are four or more card types among cards in your graveyard, each opponent's maximum hand size is equal to seven minus the number of those card types.
        this.addAbility(new SimpleStaticAbility(new WinterMisanthropicGuideEffect())
                .setAbilityWord(AbilityWord.DELIRIUM)
                .addHint(CardTypesInGraveyardCount.YOU.getHint()));
    }

    private WinterMisanthropicGuide(final WinterMisanthropicGuide card) {
        super(card);
    }

    @Override
    public WinterMisanthropicGuide copy() {
        return new WinterMisanthropicGuide(this);
    }
}

class WinterMisanthropicGuideEffect extends ContinuousEffectImpl {

    WinterMisanthropicGuideEffect() {
        super(Duration.WhileOnBattlefield, Layer.PlayerEffects, SubLayer.NA, Outcome.Neutral);
        staticText = "as long as there are four or more card types among cards in your graveyard, " +
                "each opponent's maximum hand size is equal to seven minus the number of those card types";
    }

    private WinterMisanthropicGuideEffect(final WinterMisanthropicGuideEffect effect) {
        super(effect);
    }

    @Override
    public WinterMisanthropicGuideEffect copy() {
        return new WinterMisanthropicGuideEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int count = CardTypesInGraveyardCount.YOU.calculate(game, source, this);
        if (count < 4) {
            return false;
        }
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(opponentId);
            if (player == null) {
                continue;
            }
            player.setMaxHandSize(Math.max(7 - count, 0));
        }
        return true;
    }
}
