package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.CantGainLifeAllEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author LevelX2
 */
public final class RampagingFerocidon extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("another creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public RampagingFerocidon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Players can't gain life.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantGainLifeAllEffect()));

        // Whenever another creature enters the battlefield, Rampaging Ferocidon deals 1 damage to that creature's controller.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD, new DamageTargetEffect(1, true, "that creature's controller"), filter, false, SetTargetPointer.PLAYER, ""));
    }

    private RampagingFerocidon(final RampagingFerocidon card) {
        super(card);
    }

    @Override
    public RampagingFerocidon copy() {
        return new RampagingFerocidon(this);
    }
}
