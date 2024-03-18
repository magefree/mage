package mage.cards.p;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.DisguiseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanentThisOrAnother;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author Cguy7777
 */
public final class PyrotechnicPerformer extends CardImpl {

    private static final FilterPermanentThisOrAnother filter = new FilterPermanentThisOrAnother(StaticFilters.FILTER_CONTROLLED_CREATURE, true);

    public PyrotechnicPerformer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.VIASHINO);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Disguise {R}
        this.addAbility(new DisguiseAbility(this, new ManaCostsImpl<>("{R}")));

        // Whenever Pyrotechnic Performer or another creature you control is turned face up, that creature deals damage equal to its power to each opponent.
        this.addAbility(new TurnedFaceUpAllTriggeredAbility(new PyrotechnicPerformerEffect(), filter, true));
    }

    private PyrotechnicPerformer(final PyrotechnicPerformer card) {
        super(card);
    }

    @Override
    public PyrotechnicPerformer copy() {
        return new PyrotechnicPerformer(this);
    }
}

class PyrotechnicPerformerEffect extends OneShotEffect {

    PyrotechnicPerformerEffect() {
        super(Outcome.Damage);
        staticText = "that creature deals damage equal to its power to each opponent";
    }

    private PyrotechnicPerformerEffect(final PyrotechnicPerformerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent turnedUpCreature = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (turnedUpCreature == null) {
            return false;
        }

        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(playerId);
            if (opponent == null) {
                continue;
            }
            opponent.damage(turnedUpCreature.getPower().getValue(), turnedUpCreature.getId(), source, game);
        }
        return true;
    }

    @Override
    public PyrotechnicPerformerEffect copy() {
        return new PyrotechnicPerformerEffect(this);
    }
}
