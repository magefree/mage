
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ChooseNewTargetsTargetEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterStackObject;
import mage.filter.predicate.other.NumberOfTargetsPredicate;
import mage.target.TargetStackObject;

/**
 *
 * @author LevelX2
 */
public final class Willbender extends CardImpl {

    private static final FilterStackObject FILTER = new FilterStackObject("spell or ability with a single target");

    static {
        FILTER.add(new NumberOfTargetsPredicate(1));
    }

    public Willbender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Morph {1}{U}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{1}{U}")));
        // When Willbender is turned face up, change the target of target spell or ability with a single target.
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(new ChooseNewTargetsTargetEffect(true, true));
        ability.addTarget(new TargetStackObject(FILTER));
        this.addAbility(ability);

    }

    private Willbender(final Willbender card) {
        super(card);
    }

    @Override
    public Willbender copy() {
        return new Willbender(this);
    }
}
