
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.OrCondition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author spjspj
 */
public final class SolitaryCamel extends CardImpl {

    private static final FilterControlledPermanent filterDesertPermanent = new FilterControlledPermanent("Desert");
    private static final FilterCard filterDesertCard = new FilterCard("Desert card");

    static {
        filterDesertPermanent.add(SubType.DESERT.getPredicate());
        filterDesertCard.add(SubType.DESERT.getPredicate());
    }

    public SolitaryCamel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.CAMEL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Solitary Camel has lifelink as long as you control a desert or there is a desert card in your graveyard.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(LifelinkAbility.getInstance()),
                new OrCondition(
                        new PermanentsOnTheBattlefieldCondition(new FilterControlledPermanent(filterDesertPermanent)),
                        new CardsInControllerGraveyardCondition(1, filterDesertCard)),
                "{this} has lifelink as long as you control a desert or there is a desert card in your graveyard."));
        this.addAbility(ability);
    }

    public SolitaryCamel(final SolitaryCamel card) {
        super(card);
    }

    @Override
    public SolitaryCamel copy() {
        return new SolitaryCamel(this);
    }
}
