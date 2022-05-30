
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.ShroudAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author cbt33
 */
public final class CrashingCentaur extends CardImpl {

    public CrashingCentaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.CENTAUR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // {G}, Discard a card: Crashing Centaur gains trample until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(TrampleAbility.getInstance(),Duration.EndOfTurn), new ManaCostsImpl<>("{G}"));
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);

        // Threshold - As long as seven or more cards are in your graveyard, Crashing Centaur gets +2/+2 and has shroud.
        Ability thresholdAbility = new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                    new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield),
                    new CardsInControllerGraveyardCondition(7),
                    "If seven or more cards are in your graveyard, {this} gets +2/+2"));
                Effect effect = new ConditionalContinuousEffect(
                                        new GainAbilitySourceEffect(ShroudAbility.getInstance()),
                                        new CardsInControllerGraveyardCondition(7), "and has shroud");
        thresholdAbility.addEffect(effect);
        thresholdAbility.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(thresholdAbility);
    }

    private CrashingCentaur(final CrashingCentaur card) {
        super(card);
    }

    @Override
    public CrashingCentaur copy() {
        return new CrashingCentaur(this);
    }
}
