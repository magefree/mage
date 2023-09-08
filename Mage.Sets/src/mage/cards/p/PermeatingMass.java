package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToACreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.functions.EmptyCopyApplier;

/**
 *
 * @author fireshoes
 */
public final class PermeatingMass extends CardImpl {

    public PermeatingMass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever Permeating Mass deals combat damage to a creature, that creature becomes a copy of Permeating Mass.
        this.addAbility(new DealsCombatDamageToACreatureTriggeredAbility(new PermeatingMassEffect(), false, true));
    }

    private PermeatingMass(final PermeatingMass card) {
        super(card);
    }

    @Override
    public PermeatingMass copy() {
        return new PermeatingMass(this);
    }
}

class PermeatingMassEffect extends OneShotEffect {

    public PermeatingMassEffect() {
        super(Outcome.Copy);
        this.staticText = "that creature becomes a copy of {this}.";
    }

    private PermeatingMassEffect(final PermeatingMassEffect effect) {
        super(effect);
    }

    @Override
    public PermeatingMassEffect copy() {
        return new PermeatingMassEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability ability) {
        Permanent copyTo = game.getPermanent(getTargetPointer().getFirst(game, ability));
        if (copyTo != null) {
            Permanent copyFrom = ability.getSourcePermanentOrLKI(game);
            if (copyFrom != null) {
                game.copyPermanent(Duration.Custom, copyFrom, copyTo.getId(), ability, new EmptyCopyApplier());
            }
        }
        return true;
    }
}
