package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.effects.common.combat.GoadAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.game.Game;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TauntFromTheRampart extends CardImpl {

    public TauntFromTheRampart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{W}");

        // Goad all creatures your opponents control. Until your next turn, those creatures can't block.
        this.getSpellAbility().addEffect(new TauntFromTheRampartEffect());
    }

    private TauntFromTheRampart(final TauntFromTheRampart card) {
        super(card);
    }

    @Override
    public TauntFromTheRampart copy() {
        return new TauntFromTheRampart(this);
    }
}

class TauntFromTheRampartEffect extends GoadAllEffect {

    private static final FilterPermanent filter1 = new FilterOpponentsCreaturePermanent(
            "creatures your opponents control. Until your next turn, those creatures can’t block"
    );

    TauntFromTheRampartEffect() {
        super(Duration.UntilYourNextTurn, filter1);
    }

    protected TauntFromTheRampartEffect(final TauntFromTheRampartEffect effect) {
        super(effect);
    }

    @Override
    public TauntFromTheRampartEffect copy() {
        return new TauntFromTheRampartEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        game.addEffect(new CantBlockTargetEffect(Duration.UntilYourNextTurn)
                .setTargetPointer(new FixedTargets(affectedObjectList)), source);
    }

}
