package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class AngryMob extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Swamps you opponents control");

    static {
        filter.add(SubType.SWAMP.getPredicate());
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public AngryMob(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // As long as it's your turn, Angry Mob's power and toughness are each equal to 2 plus the number of Swamps your opponents control. As long as it's not your turn, Angry Mob's power and toughness are each 2. 
        PermanentsOnBattlefieldCount swamps = new PermanentsOnBattlefieldCount(filter);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostSourceEffect(swamps, swamps, Duration.WhileOnBattlefield),
                MyTurnCondition.instance,
                "As long as it's your turn, Angry Mob's power and toughness are each equal to 2 plus the number of Swamps your opponents control. As long as it's not your turn, Angry Mob's power and toughness are each 2"))
                .addHint(MyTurnHint.instance));

    }

    private AngryMob(final AngryMob card) {
        super(card);
    }

    @Override
    public AngryMob copy() {
        return new AngryMob(this);
    }
}
