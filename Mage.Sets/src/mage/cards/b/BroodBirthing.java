
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.token.EldraziSpawnToken;

/**
 *
 * @author North
 */
public final class BroodBirthing extends CardImpl {

    public BroodBirthing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}");


        this.getSpellAbility().addEffect(new BroodBirthingEffect());
    }

    private BroodBirthing(final BroodBirthing card) {
        super(card);
    }

    @Override
    public BroodBirthing copy() {
        return new BroodBirthing(this);
    }
}

class BroodBirthingEffect extends OneShotEffect {

    public BroodBirthingEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "If you control an Eldrazi Spawn, create three 0/1 colorless Eldrazi Spawn creature tokens. They have \"Sacrifice this creature: Add {C}.\" Otherwise, create one of those tokens";
    }

    private BroodBirthingEffect(final BroodBirthingEffect effect) {
        super(effect);
    }

    @Override
    public BroodBirthingEffect copy() {
        return new BroodBirthingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Eldrazi Spawn");
        filter.add(SubType.ELDRAZI.getPredicate());
        filter.add(SubType.SPAWN.getPredicate());

        EldraziSpawnToken token = new EldraziSpawnToken();
        int count = game.getBattlefield().countAll(filter, source.getControllerId(), game) > 0 ? 3 : 1;
        token.putOntoBattlefield(count, game, source, source.getControllerId());
        return true;
    }
}
