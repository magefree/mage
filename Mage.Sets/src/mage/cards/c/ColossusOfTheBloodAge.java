package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class ColossusOfTheBloodAge extends CardImpl {

    public ColossusOfTheBloodAge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{R}{W}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // When this creature enters, it deals 3 damage to each opponent and you gain 3 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(
            new DamagePlayersEffect(3, TargetController.OPPONENT, "it")
        );
        ability.addEffect(new GainLifeEffect(3));
        this.addAbility(ability);

        // When this creature dies, discard any number of cards, then draw that many cards plus one.
        this.addAbility(new DiesSourceTriggeredAbility(new ColossusOfTheBloodAgeEffect()));
    }

    private ColossusOfTheBloodAge(final ColossusOfTheBloodAge card) {
        super(card);
    }

    @Override
    public ColossusOfTheBloodAge copy() {
        return new ColossusOfTheBloodAge(this);
    }
}

class ColossusOfTheBloodAgeEffect extends OneShotEffect {

    ColossusOfTheBloodAgeEffect() {
        super(Outcome.DrawCard);
        staticText = "discard any number of cards, then draw that many cards plus one";
    }

    private ColossusOfTheBloodAgeEffect(final ColossusOfTheBloodAgeEffect effect) {
        super(effect);
    }

    @Override
    public ColossusOfTheBloodAgeEffect copy() {
        return new ColossusOfTheBloodAgeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int discarded = player.discard(0, Integer.MAX_VALUE, false, source, game).size();
        game.processAction();
        player.drawCards(discarded + 1, source, game);
        return true;
    }
}
