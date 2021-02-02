
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author Backfir3
 */
public final class Valor extends CardImpl {

    private static final String ruleText = "As long as Valor is in your graveyard and you control a Plains, creatures you control have first strike";

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Plains");

    static {
        filter.add(CardType.LAND.getPredicate());
        filter.add(SubType.PLAINS.getPredicate());
    }

    public Valor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.INCARNATION);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // As long as Valor is in your graveyard and you control a Plains, creatures you control have first strike
        ContinuousEffect effect = new GainAbilityControlledEffect(FirstStrikeAbility.getInstance(),
                Duration.WhileOnBattlefield, new FilterCreaturePermanent());
        ConditionalContinuousEffect valorEffect = new ConditionalContinuousEffect(effect,
                new PermanentsOnTheBattlefieldCondition(filter), ruleText);
        this.addAbility(new SimpleStaticAbility(Zone.GRAVEYARD, valorEffect));
    }

    private Valor(final Valor card) {
        super(card);
    }

    @Override
    public Valor copy() {
        return new Valor(this);
    }
}
