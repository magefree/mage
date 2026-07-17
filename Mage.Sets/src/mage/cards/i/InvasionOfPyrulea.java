package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.TransformedPredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfPyrulea extends TransformingDoubleFacedCard {

    public InvasionOfPyrulea(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.BATTLE}, new SubType[]{SubType.SIEGE}, "{G}{U}",
                "Gargantuan Slabhorn",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.BEAST}, "GU"
        );

        // Invasion of Pyrulea
        this.getLeftHalfCard().setStartingDefense(4);

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.getLeftHalfCard().addAbility(new SiegeAbility());

        // When Invasion of Pyrulea enters the battlefield, scry 3, then reveal the top card of your library. If it's a land or double-faced card, draw a card.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new InvasionOfPyruleaEffect()));

        // Gargantuan Slabhorn
        this.getRightHalfCard().setPT(4, 4);

        // Trample
        this.getRightHalfCard().addAbility(TrampleAbility.getInstance());

        // Ward {2}
        this.getRightHalfCard().addAbility(new WardAbility(new ManaCostsImpl<>("{2}"), false));

        // Other transformed permanents you control have trample and ward {2}.
        FilterPermanent filter = new FilterPermanent("transformed permanents");
        filter.add(TransformedPredicate.instance);
        Ability ability = new SimpleStaticAbility(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.WhileOnBattlefield, filter, true
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                new WardAbility(new GenericManaCost(2)), Duration.WhileOnBattlefield, filter, true
        ).setText("and ward {2}"));
        this.getRightHalfCard().addAbility(ability);
    }

    private InvasionOfPyrulea(final InvasionOfPyrulea card) {
        super(card);
    }

    @Override
    public InvasionOfPyrulea copy() {
        return new InvasionOfPyrulea(this);
    }
}

class InvasionOfPyruleaEffect extends OneShotEffect {

    InvasionOfPyruleaEffect() {
        super(Outcome.Benefit);
        staticText = "scry 3, then reveal the top card of your library. If it's a land or double-faced card, draw a card";
    }

    private InvasionOfPyruleaEffect(final InvasionOfPyruleaEffect effect) {
        super(effect);
    }

    @Override
    public InvasionOfPyruleaEffect copy() {
        return new InvasionOfPyruleaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.scry(3, source, game);
        Card card = player.getLibrary().getFromTop(game);
        player.revealCards(source, new CardsImpl(card), game);
        if (card != null && (card.isLand(game) || card instanceof DoubleFacedCard || card.getSecondCardFace() != null)) {
            player.drawCards(1, source, game);
        }
        return true;
    }
}
