package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class CabalPit extends CardImpl {

    public CabalPit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {tap}: Add {B}. Cabal Pit deals 1 damage to you.
        Ability manaAbility = new BlackManaAbility();
        manaAbility.addEffect(new DamageControllerEffect(1));
        this.addAbility(manaAbility);

        // Threshold - {B}, {T}, Sacrifice Cabal Pit: Target creature gets -2/-2 until end of turn. Activate this ability only if seven or more cards are in your graveyard.
        Ability thresholdAbility = new ConditionalActivatedAbility(
                new BoostTargetEffect(-2, -2, Duration.EndOfTurn),
                new ManaCostsImpl<>("{B}"), ThresholdCondition.instance
        );
        thresholdAbility.addCost(new TapSourceCost());
        thresholdAbility.addCost(new SacrificeSourceCost());
        thresholdAbility.addTarget(new TargetCreaturePermanent());
        thresholdAbility.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(thresholdAbility);
    }

    private CabalPit(final CabalPit card) {
        super(card);
    }

    @Override
    public CabalPit copy() {
        return new CabalPit(this);
    }
}
