package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.MaxSpeedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
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
public final class LoxodonSurveyor extends CardImpl {

    public LoxodonSurveyor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ELEPHANT);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Start your engines!
        this.addAbility(new StartYourEnginesAbility());

        // Max speed -- {3}, Exile this card from your graveyard: Draw a card.
        Ability ability = new SimpleActivatedAbility(
                Zone.GRAVEYARD, new DrawCardSourceControllerEffect(1), new GenericManaCost(3)
        );
        ability.addCost(new ExileSourceFromGraveCost());
        this.addAbility(new MaxSpeedAbility(ability));
    }

    private LoxodonSurveyor(final LoxodonSurveyor card) {
        super(card);
    }

    @Override
    public LoxodonSurveyor copy() {
        return new LoxodonSurveyor(this);
    }
}
