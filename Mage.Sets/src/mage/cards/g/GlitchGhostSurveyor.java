package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.MaxSpeedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.StartYourEnginesAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GlitchGhostSurveyor extends CardImpl {

    public GlitchGhostSurveyor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Start your engines!
        this.addAbility(new StartYourEnginesAbility());

        // Max speed -- {3}, Exile this card from your graveyard: Draw a card.
        Ability ability = new SimpleActivatedAbility(
                Zone.GRAVEYARD, new DrawCardSourceControllerEffect(1), new GenericManaCost(3)
        );
        ability.addCost(new ExileSourceFromGraveCost());
        this.addAbility(new MaxSpeedAbility(ability));
    }

    private GlitchGhostSurveyor(final GlitchGhostSurveyor card) {
        super(card);
    }

    @Override
    public GlitchGhostSurveyor copy() {
        return new GlitchGhostSurveyor(this);
    }
}
