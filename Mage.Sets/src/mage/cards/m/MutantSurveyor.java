package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.MaxSpeedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
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
public final class MutantSurveyor extends CardImpl {

    public MutantSurveyor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Start your engines!
        this.addAbility(new StartYourEnginesAbility());

        // {2}: This creature gets +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BoostSourceEffect(
                1, 1, Duration.EndOfTurn
        ), new GenericManaCost(2)));

        // Max speed -- {3}, Exile this card from your graveyard: Draw a card.
        Ability ability = new SimpleActivatedAbility(
                Zone.GRAVEYARD, new DrawCardSourceControllerEffect(1), new GenericManaCost(3)
        );
        ability.addCost(new ExileSourceFromGraveCost());
        this.addAbility(new MaxSpeedAbility(ability));
    }

    private MutantSurveyor(final MutantSurveyor card) {
        super(card);
    }

    @Override
    public MutantSurveyor copy() {
        return new MutantSurveyor(this);
    }
}
