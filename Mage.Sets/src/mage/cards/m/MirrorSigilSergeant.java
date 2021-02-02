
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author jeffwadsworth
 */
public final class MirrorSigilSergeant extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("blue permanent");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    private static final String rule = "At the beginning of your upkeep, if you control a blue permanent, you may create a token that's a copy of {this}.";

    public MirrorSigilSergeant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");
        this.subtype.add(SubType.RHINO);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of your upkeep, if you control a blue permanent, you may create a token that's a copy of Mirror-Sigil Sergeant.
        Effect effect = new CreateTokenCopySourceEffect();
        effect.setText("you may create a token that's a copy of {this}");
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, effect, TargetController.YOU, true);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, new PermanentsOnTheBattlefieldCondition(filter), rule));

    }

    private MirrorSigilSergeant(final MirrorSigilSergeant card) {
        super(card);
    }

    @Override
    public MirrorSigilSergeant copy() {
        return new MirrorSigilSergeant(this);
    }
}
