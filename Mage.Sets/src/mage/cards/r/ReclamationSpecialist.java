package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author muz
 */
public final class ReclamationSpecialist extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("an artifact creature");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public ReclamationSpecialist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.BORG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}, Sacrifice an artifact creature: Draw a card.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new GenericManaCost(1));
        ability.addCost(new SacrificeTargetCost(filter));
        this.addAbility(ability);
    }

    private ReclamationSpecialist(final ReclamationSpecialist card) {
        super(card);
    }

    @Override
    public ReclamationSpecialist copy() {
        return new ReclamationSpecialist(this);
    }
}
