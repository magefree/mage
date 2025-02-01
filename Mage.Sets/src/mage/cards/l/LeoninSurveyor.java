package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.MaxSpeedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.StartYourEnginesAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LeoninSurveyor extends CardImpl {

    public LeoninSurveyor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Start your engines!
        this.addAbility(new StartYourEnginesAbility());

        // During your turn, this creature has first strike.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield),
                MyTurnCondition.instance, "during your turn, {this} has first strike."
        )));

        // Max speed -- {3}, Exile this card from your graveyard: Draw a card.
        Ability ability = new SimpleActivatedAbility(
                Zone.GRAVEYARD, new DrawCardSourceControllerEffect(1), new GenericManaCost(3)
        );
        ability.addCost(new ExileSourceFromGraveCost());
        this.addAbility(new MaxSpeedAbility(ability));
    }

    private LeoninSurveyor(final LeoninSurveyor card) {
        super(card);
    }

    @Override
    public LeoninSurveyor copy() {
        return new LeoninSurveyor(this);
    }
}
