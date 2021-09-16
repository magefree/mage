package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturesWithDifferentPowers;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SigardianZealot extends CardImpl {

    public SigardianZealot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of combat on your turn, choose any number of creatures with different powers. Each of them gets +X/+X and gains vigilance until end of turn, where X is Sigardian Zealot's power.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                new SigardianZealotEffect(), TargetController.YOU, false
        ));
    }

    private SigardianZealot(final SigardianZealot card) {
        super(card);
    }

    @Override
    public SigardianZealot copy() {
        return new SigardianZealot(this);
    }
}

class SigardianZealotEffect extends OneShotEffect {

    SigardianZealotEffect() {
        super(Outcome.Benefit);
        staticText = "choose any number of creatures with different powers. " +
                "Each of them gets +X/+X and gains vigilance until end of turn, where X is {this}'s power";
    }

    private SigardianZealotEffect(final SigardianZealotEffect effect) {
        super(effect);
    }

    @Override
    public SigardianZealotEffect copy() {
        return new SigardianZealotEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (player == null || permanent == null) {
            return false;
        }
        int power = permanent.getPower().getValue();
        if (power == 0) {
            return false;
        }
        TargetPermanent target = new TargetCreaturesWithDifferentPowers();
        player.choose(outcome, target, source.getSourceId(), game);
        Cards cards = new CardsImpl(target.getTargets());
        if (cards.isEmpty()) {
            return false;
        }
        game.addEffect(new BoostTargetEffect(power, power).setTargetPointer(new FixedTargets(cards, game)), source);
        game.addEffect(new GainAbilityTargetEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn
        ).setTargetPointer(new FixedTargets(cards, game)), source);
        return true;
    }
}
