package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.common.CovenHint;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturesWithDifferentPowers;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SigardasVanguard extends CardImpl {

    public SigardasVanguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Sigarda's Vanguard enters the battlefield or attacks, choose any number of creatures with different powers. Those creatures gain double strike until end of turn.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new SigardasVanguardEffect()).addHint(CovenHint.instance));
    }

    private SigardasVanguard(final SigardasVanguard card) {
        super(card);
    }

    @Override
    public SigardasVanguard copy() {
        return new SigardasVanguard(this);
    }
}

class SigardasVanguardEffect extends OneShotEffect {

    SigardasVanguardEffect() {
        super(Outcome.Benefit);
        staticText = "choose any number of creatures with different powers. " +
                "Those creatures gain double strike until end of turn";
    }

    private SigardasVanguardEffect(final SigardasVanguardEffect effect) {
        super(effect);
    }

    @Override
    public SigardasVanguardEffect copy() {
        return new SigardasVanguardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetCreaturesWithDifferentPowers();
        player.choose(outcome, target, source.getSourceId(), game);
        if (target.getTargets().isEmpty()) {
            return false;
        }
        game.addEffect(new GainAbilityTargetEffect(
                DoubleStrikeAbility.getInstance(), Duration.EndOfTurn
        ).setTargetPointer(new FixedTargets(new CardsImpl(target.getTargets()), game)), source);
        return true;
    }
}
