package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.SpellsCostModificationThatTargetSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.token.PursuedWhaleToken;
import mage.game.permanent.token.Token;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PursuedWhale extends CardImpl {

    public PursuedWhale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");

        this.subtype.add(SubType.WHALE);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // When Pursued Whale enters the battlefield, each opponent creates a 1/1 red Pirate creature token with "This creature can't block" and "Creatures you control attack each combat if able."
        this.addAbility(new EntersBattlefieldTriggeredAbility(new PursuedWhaleTokenEffect()));

        // Spells your opponents cast that target Pursued Whale cost {3} more to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new SpellsCostModificationThatTargetSourceEffect(3, new FilterCard("Spells"), TargetController.OPPONENT))
        );
    }

    private PursuedWhale(final PursuedWhale card) {
        super(card);
    }

    @Override
    public PursuedWhale copy() {
        return new PursuedWhale(this);
    }
}

class PursuedWhaleTokenEffect extends OneShotEffect {

    private static final Token token = new PursuedWhaleToken();

    PursuedWhaleTokenEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent creates a 1/1 red Pirate creature token with " +
                "\"This creature can't block\" and \"Creatures you control attack each combat if able.\"";
    }

    private PursuedWhaleTokenEffect(final PursuedWhaleTokenEffect effect) {
        super(effect);
    }

    @Override
    public PursuedWhaleTokenEffect copy() {
        return new PursuedWhaleTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            token.putOntoBattlefield(1, game, source, playerId);
        }
        return true;
    }
}
