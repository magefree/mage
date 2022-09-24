
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HauntAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author LevelX2
 */
public final class GravenDominator extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("each other creature");
    static {
        filter.add(AnotherPredicate.instance);
    }

    public GravenDominator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}{W}");
        this.subtype.add(SubType.GARGOYLE);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Haunt
        // When Graven Dominator enters the battlefield or the creature it haunts dies, each other creature has base power and toughness 1/1 until end of turn.
        Ability ability = new HauntAbility(this, new SetBasePowerToughnessAllEffect(1,1, Duration.EndOfTurn, filter, true));
        this.addAbility(ability);
    }

    private GravenDominator(final GravenDominator card) {
        super(card);
    }

    @Override
    public GravenDominator copy() {
        return new GravenDominator(this);
    }
}
