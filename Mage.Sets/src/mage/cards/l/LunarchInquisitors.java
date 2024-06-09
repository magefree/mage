package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TransformIntoSourceTriggeredAbility;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class LunarchInquisitors extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("another target creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public LunarchInquisitors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.color.setWhite(true);

        // this card is the second face of double-faced card
        this.nightCard = true;

        // When this creature transforms into Lunarch Inquisitors, you may exile another target creature until Lunarch Inquisitors leaves the battlefield.
        Ability ability = new TransformIntoSourceTriggeredAbility(new ExileUntilSourceLeavesEffect(), true);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private LunarchInquisitors(final LunarchInquisitors card) {
        super(card);
    }

    @Override
    public LunarchInquisitors copy() {
        return new LunarchInquisitors(this);
    }
}
