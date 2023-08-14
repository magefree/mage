package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.UnearthAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MishraTamerOfMakFawa extends CardImpl {

    public MishraTamerOfMakFawa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Permanents you control have "Ward--Sacrifice a permanent."
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new WardAbility(new SacrificeTargetCost(
                        StaticFilters.FILTER_CONTROLLED_PERMANENT_SHORT_TEXT
                ), false), Duration.WhileOnBattlefield
        )));

        // Each artifact card in your graveyard has unearth {1}{B}{R}
        this.addAbility(new SimpleStaticAbility(new MishraTamerOfMakFawaEffect()));
    }

    private MishraTamerOfMakFawa(final MishraTamerOfMakFawa card) {
        super(card);
    }

    @Override
    public MishraTamerOfMakFawa copy() {
        return new MishraTamerOfMakFawa(this);
    }
}

class MishraTamerOfMakFawaEffect extends ContinuousEffectImpl {

    MishraTamerOfMakFawaEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.Benefit);
        staticText = "each artifact card in your graveyard has unearth {1}{B}{R}";
    }

    private MishraTamerOfMakFawaEffect(final MishraTamerOfMakFawaEffect effect) {
        super(effect);
    }

    @Override
    public MishraTamerOfMakFawaEffect copy() {
        return new MishraTamerOfMakFawaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        for (Card card : player.getGraveyard().getCards(StaticFilters.FILTER_CARD_ARTIFACT, game)) {
            game.getState().addOtherAbility(card, new UnearthAbility(new ManaCostsImpl<>("{1}{B}{R}")));
        }
        return true;
    }
}
