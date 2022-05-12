package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.CitizenGreenWhiteToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CouriersBriefcase extends CardImpl {

    public CouriersBriefcase(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{G}");

        this.subtype.add(SubType.TREASURE);

        // When Courier's Briefcase enters the battlefield, create a 1/1 green and white Citizen creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new CitizenGreenWhiteToken())));

        // {T}, Sacrifice Courier's Briefcase: Add one mana of any color.
        Ability ability = new AnyColorManaAbility();
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

        // {W}{U}{B}{R}{G}, {T}, Sacrifice Courier's Briefcase: Draw three cards.
        ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(3), new ManaCostsImpl<>("{W}{U}{B}{R}{G}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private CouriersBriefcase(final CouriersBriefcase card) {
        super(card);
    }

    @Override
    public CouriersBriefcase copy() {
        return new CouriersBriefcase(this);
    }
}
