package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetPlaneswalkerPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MishrasCommand extends CardImpl {

    public MishrasCommand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}");

        // Choose two --
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);

        // * Choose target player. They may discard up to X cards. Then they draw a card for each card discarded this way.
        this.getSpellAbility().addEffect(new MishrasCommandEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

        // * This spell deals X damage to target creature.
        this.getSpellAbility().addMode(new Mode(new DamageTargetEffect(
                ManacostVariableValue.REGULAR, "this spell"
        )).addTarget(new TargetCreaturePermanent()));

        // * This spell deals X damage to target planeswalker.
        this.getSpellAbility().addMode(new Mode(new DamageTargetEffect(
                ManacostVariableValue.REGULAR, "this spell"
        )).addTarget(new TargetPlaneswalkerPermanent()));

        // * Target creature gets +X/+0 and gains haste until end of turn.
        this.getSpellAbility().addMode(new Mode(new BoostTargetEffect(
                ManacostVariableValue.REGULAR, StaticValue.get(0), Duration.EndOfTurn
        ).setText("target creature gets +X/+0")).addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains haste until end of turn")).addTarget(new TargetCreaturePermanent()));
    }

    private MishrasCommand(final MishrasCommand card) {
        super(card);
    }

    @Override
    public MishrasCommand copy() {
        return new MishrasCommand(this);
    }
}

class MishrasCommandEffect extends OneShotEffect {

    MishrasCommandEffect() {
        super(Outcome.Benefit);
        staticText = "choose target player. They may discard up to X cards. " +
                "Then they draw a card for each card discarded this way";
    }

    private MishrasCommandEffect(final MishrasCommandEffect effect) {
        super(effect);
    }

    @Override
    public MishrasCommandEffect copy() {
        return new MishrasCommandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        int xValue = source.getManaCostsToPay().getX();
        if (player != null && xValue > 0) {
            player.drawCards(player.discard(
                    0, xValue, false, source, game
            ).size(), source, game);
            return true;
        }
        return false;
    }
}
