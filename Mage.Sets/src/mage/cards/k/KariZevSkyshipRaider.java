
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.permanent.token.RagavanToken;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class KariZevSkyshipRaider extends CardImpl {

    public KariZevSkyshipRaider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever Kari Zev, Skyship Raider attacks, create a legendary 2/1 red Monkey creature token named Ragavan that's tapped and attacking. Exile that token at end of combat.
        this.addAbility(new AttacksTriggeredAbility(new KariZevSkyshipRaiderEffect(), false));
    }

    private KariZevSkyshipRaider(final KariZevSkyshipRaider card) {
        super(card);
    }

    @Override
    public KariZevSkyshipRaider copy() {
        return new KariZevSkyshipRaider(this);
    }
}

class KariZevSkyshipRaiderEffect extends OneShotEffect {

    KariZevSkyshipRaiderEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "create Ragavan, a legendary 2/1 red Monkey creature token. Ragavan enters the battlefield tapped and attacking. Exile that token at end of combat";
    }

    KariZevSkyshipRaiderEffect(final KariZevSkyshipRaiderEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CreateTokenEffect effect = new CreateTokenEffect(new RagavanToken(), 1, true, true);
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && effect.apply(game, source)) {
            effect.exileTokensCreatedAtEndOfCombat(game, source);
            return true;
        }
        return false;
    }

    @Override
    public KariZevSkyshipRaiderEffect copy() {
        return new KariZevSkyshipRaiderEffect(this);
    }
}
