package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.BasicLandcyclingAbility;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author spjspj
 */
public final class VileManifestation extends CardImpl {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(mage.filter.predicate.Predicates.or(
                new AbilityPredicate(CyclingAbility.class),
                new AbilityPredicate(BasicLandcyclingAbility.class)));
    }

    public VileManifestation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Vile Manifestation gets +1/+0 for each card with cycling in your graveyard.
        DynamicValue amount = new CardsInControllerGraveyardCount(new FilterCard(filter));
        Effect effect = new BoostSourceEffect(amount, StaticValue.get(0), Duration.WhileOnBattlefield);
        effect.setText("Vile Manifestation gets +1/+0 for each card with cycling in your graveyard.");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        this.addAbility(ability);

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));

    }

    private VileManifestation(final VileManifestation card) {
        super(card);
    }

    @Override
    public VileManifestation copy() {
        return new VileManifestation(this);
    }
}
