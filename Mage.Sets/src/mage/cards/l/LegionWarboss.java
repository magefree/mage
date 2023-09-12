package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.constants.SubType;
import mage.abilities.keyword.MentorAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.GoblinToken;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class LegionWarboss extends CardImpl {

    public LegionWarboss(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Mentor
        this.addAbility(new MentorAbility());

        // At the beginning of combat on your turn, create a 1/1 red Goblin creature token. That token gains haste until end of turn and attacks this combat if able.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                new LegionWarbossEffect(),
                TargetController.YOU, false
        ));
    }

    private LegionWarboss(final LegionWarboss card) {
        super(card);
    }

    @Override
    public LegionWarboss copy() {
        return new LegionWarboss(this);
    }
}

class LegionWarbossEffect extends OneShotEffect {

    public LegionWarbossEffect() {
        super(Outcome.Benefit);
        staticText = "create a 1/1 red Goblin creature token. "
                + "That token gains haste until end of turn "
                + "and attacks this combat if able";
    }

    private LegionWarbossEffect(final LegionWarbossEffect effect) {
        super(effect);
    }

    @Override
    public LegionWarbossEffect copy() {
        return new LegionWarbossEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CreateTokenEffect effect = new CreateTokenEffect(new GoblinToken());
        effect.apply(game, source);
        effect.getLastAddedTokenIds().stream().map((tokenId) -> {
            ContinuousEffect continuousEffect = new GainAbilityTargetEffect(
                    HasteAbility.getInstance(), Duration.EndOfTurn
            );
            continuousEffect.setTargetPointer(new FixedTarget(tokenId, game));
            return continuousEffect;
        }).forEachOrdered((continuousEffect) -> {
            game.addEffect(continuousEffect, source);
        });
        effect.getLastAddedTokenIds().stream().map((tokenId) -> {
            ContinuousEffect continuousEffect = new GainAbilityTargetEffect(
                    new LegionWarbossAbility(), Duration.EndOfCombat
            );
            continuousEffect.setTargetPointer(new FixedTarget(tokenId, game));
            return continuousEffect;
        }).forEachOrdered((continuousEffect) -> {
            game.addEffect(continuousEffect, source);
        });
        return true;
    }
}

class LegionWarbossAbility extends StaticAbility {

    public LegionWarbossAbility() {
        super(Zone.BATTLEFIELD, new AttacksIfAbleSourceEffect(
                Duration.WhileOnBattlefield, true
        ).setText("this creature attacks this combat if able"));
    }

    private LegionWarbossAbility(final LegionWarbossAbility ability) {
        super(ability);
    }

    @Override
    public LegionWarbossAbility copy() {
        return new LegionWarbossAbility(this);
    }
}
