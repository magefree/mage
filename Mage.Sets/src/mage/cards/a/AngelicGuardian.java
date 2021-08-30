package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.Objects;
import java.util.UUID;

/**
 * @author JayDi85
 */
public final class AngelicGuardian extends CardImpl {

    public AngelicGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever one or more creatures you control attack, they gain indestructible until end of turn
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
                new AngelicGuardianGainEffect(), 1
        ).setTriggerPhrase("Whenever one or more creatures you control attack, "));
    }

    private AngelicGuardian(final AngelicGuardian card) {
        super(card);
    }

    @Override
    public AngelicGuardian copy() {
        return new AngelicGuardian(this);
    }
}

class AngelicGuardianGainEffect extends OneShotEffect {

    public AngelicGuardianGainEffect() {
        super(Outcome.Benefit);
        staticText = "they gain indestructible until end of turn";
    }

    public AngelicGuardianGainEffect(final AngelicGuardianGainEffect effect) {
        super(effect);
    }

    @Override
    public AngelicGuardianGainEffect copy() {
        return new AngelicGuardianGainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        if (you != null) {
            game.getCombat().getAttackers().stream()
                    .map(game::getPermanent)
                    .filter(Objects::nonNull)
                    .filter(permanent -> permanent.isControlledBy(you.getId()))
                    .filter(permanent1 -> permanent1.isCreature(game))
                    .forEach(permanent -> {
                        ContinuousEffect effect = new GainAbilityTargetEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn);
                        effect.setTargetPointer(new FixedTarget(permanent, game));
                        game.addEffect(effect, source);
                    });
            return true;
        }
        return false;
    }
}
