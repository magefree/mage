package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.hint.common.RaidHint;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.permanent.token.FoodToken;
import mage.target.common.TargetOpponent;
import mage.watchers.common.PlayerAttackedWatcher;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 *
 * @author ciaccona007
 */
public final class MidnightSnack extends CardImpl {

    public MidnightSnack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");
        

        // Raid -- At the beginning of your end step, if you attacked this turn, create a Food token.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new CreateTokenEffect(new FoodToken())
        ).withInterveningIf(RaidCondition.instance);
        this.addAbility(ability.setAbilityWord(AbilityWord.RAID).addHint(RaidHint.instance), new PlayerAttackedWatcher());

        // {2}{B}, Sacrifice this enchantment: Target opponent loses X life, where X is the amount of life you gained this turn.
        Ability sacrificeAbility = new SimpleActivatedAbility(
                new LoseLifeTargetEffect(ControllerGainedLifeCount.instance),
                new ManaCostsImpl<>("{2}{B}")
        );
        sacrificeAbility.addCost(new SacrificeSourceCost());
        sacrificeAbility.addTarget(new TargetOpponent());
        ability.addHint(ControllerGainedLifeCount.getHint());
        this.addAbility(sacrificeAbility, new PlayerGainedLifeWatcher());
    }

    private MidnightSnack(final MidnightSnack card) {
        super(card);
    }

    @Override
    public MidnightSnack copy() {
        return new MidnightSnack(this);
    }
}
