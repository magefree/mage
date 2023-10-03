
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.HauntedAngelToken;

/**
 *
 * @author LoneFox
 *
 */
public final class HauntedAngel extends CardImpl {

    public HauntedAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Haunted Angel dies, exile Haunted Angel and each other player creates a 3/3 black Angel creature token with flying.
        Ability ability = new DiesSourceTriggeredAbility(new ExileSourceEffect());
        ability.addEffect(new HauntedAngelEffect());
        this.addAbility(ability);
    }

    private HauntedAngel(final HauntedAngel card) {
        super(card);
    }

    @Override
    public HauntedAngel copy() {
        return new HauntedAngel(this);
    }
}

class HauntedAngelEffect extends OneShotEffect {

    public HauntedAngelEffect() {
        super(Outcome.Detriment);
        staticText = "and each other player creates a 3/3 black Angel creature token with flying.";
    }

    private HauntedAngelEffect(final HauntedAngelEffect copy) {
        super(copy);
    }

    @Override
    public HauntedAngelEffect copy() {
        return new HauntedAngelEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID controllerId = source.getControllerId();
        HauntedAngelToken token = new HauntedAngelToken();
        for (UUID playerId : game.getState().getPlayersInRange(controllerId, game)) {
            if (!playerId.equals(controllerId)) {
                token.putOntoBattlefield(1, game, source, playerId);
            }
        }
        return true;
    }
}
