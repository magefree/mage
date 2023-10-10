package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArcumsAstrolabe extends CardImpl {

    public ArcumsAstrolabe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{S}");

        this.supertype.add(SuperType.SNOW);

        // ({S} can be paid with one mana from a snow permanent.)
        this.addAbility(new SimpleStaticAbility(
                new InfoEffect("<i>({S} can be paid with one mana from a snow source.)</i>")
        ));

        // When Arcum's Astrolabe enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));

        // {1}, {T}: Add one mana of any color.
        Ability ability = new AnyColorManaAbility(new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private ArcumsAstrolabe(final ArcumsAstrolabe card) {
        super(card);
    }

    @Override
    public ArcumsAstrolabe copy() {
        return new ArcumsAstrolabe(this);
    }
}
