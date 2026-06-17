package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class UltimoCivilizationsEnd extends CardImpl {

    public UltimoCivilizationsEnd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Menace
        this.addAbility(new MenaceAbility());

        // When Ultimo enters, each opponent sacrifices a creature of their choice.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
            new SacrificeOpponentsEffect(StaticFilters.FILTER_PERMANENT_CREATURE)
        ));

        // {2}{B}, Discard this card: Each opponent sacrifices a creature of their choice.
        Ability ability = new SimpleActivatedAbility(
            Zone.HAND,
            new SacrificeOpponentsEffect(StaticFilters.FILTER_PERMANENT_CREATURE),
            new ManaCostsImpl<>("{2}{B}")
        );
        ability.addCost(new DiscardSourceCost());
        this.addAbility(ability);
    }

    private UltimoCivilizationsEnd(final UltimoCivilizationsEnd card) {
        super(card);
    }

    @Override
    public UltimoCivilizationsEnd copy() {
        return new UltimoCivilizationsEnd(this);
    }
}
