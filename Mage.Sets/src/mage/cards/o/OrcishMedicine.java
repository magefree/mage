package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OrcishMedicine extends CardImpl {

    public OrcishMedicine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Target creature gains your choice of lifelink or indestructible until end of turn.
        this.getSpellAbility().addEffect(new OrcishMedicineEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Amass Orcs 1.
        this.getSpellAbility().addEffect(new AmassEffect(1, SubType.ORC).concatBy("<br>"));
    }

    private OrcishMedicine(final OrcishMedicine card) {
        super(card);
    }

    @Override
    public OrcishMedicine copy() {
        return new OrcishMedicine(this);
    }
}

class OrcishMedicineEffect extends OneShotEffect {

    OrcishMedicineEffect() {
        super(Outcome.Benefit);
        staticText = "target creature gains your choice of lifelink or indestructible until end of turn";
    }

    private OrcishMedicineEffect(final OrcishMedicineEffect effect) {
        super(effect);
    }

    @Override
    public OrcishMedicineEffect copy() {
        return new OrcishMedicineEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player == null || permanent == null) {
            return false;
        }
        Ability ability = player.chooseUse(
                outcome, "Choose lifelink or indestructible", null,
                "Lifelink", "Indestructible", source, game
        ) ? LifelinkAbility.getInstance() : IndestructibleAbility.getInstance();
        game.addEffect(new GainAbilityTargetEffect(ability, Duration.EndOfTurn)
                .setTargetPointer(new FixedTarget(permanent, game)), source);
        return true;
    }
}
