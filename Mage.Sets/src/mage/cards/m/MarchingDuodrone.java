package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MarchingDuodrone extends CardImpl {

    public MarchingDuodrone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Marching Duodrone attacks, each player creates a Treasure token.
        this.addAbility(new AttacksTriggeredAbility(new MarchingDuodroneEffect()));
    }

    private MarchingDuodrone(final MarchingDuodrone card) {
        super(card);
    }

    @Override
    public MarchingDuodrone copy() {
        return new MarchingDuodrone(this);
    }
}

class MarchingDuodroneEffect extends OneShotEffect {

    MarchingDuodroneEffect() {
        super(Outcome.Benefit);
        staticText = "each player creates a Treasure token";
    }

    private MarchingDuodroneEffect(final MarchingDuodroneEffect effect) {
        super(effect);
    }

    @Override
    public MarchingDuodroneEffect copy() {
        return new MarchingDuodroneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            new TreasureToken().putOntoBattlefield(1, game, source, playerId);
        }
        return true;
    }
}
