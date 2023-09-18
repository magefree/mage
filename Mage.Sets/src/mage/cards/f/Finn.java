package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;

/**
 *
 * @author NinthWorld
 */
public final class Finn extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("{this} or another nontoken creature you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(TokenPredicate.FALSE);
    }

    public Finn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.TROOPER);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Finn or another nontoken creature you control enters the battlefield under your control, you gain 1 life for each nontoken creature you control.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD,
                new GainLifeEffect(new PermanentsOnBattlefieldCount(filter))
                    .setText("you gain 1 life for each nontoken creature you control"),
                filter, false));
    }

    private Finn(final Finn card) {
        super(card);
    }

    @Override
    public Finn copy() {
        return new Finn(this);
    }
}
