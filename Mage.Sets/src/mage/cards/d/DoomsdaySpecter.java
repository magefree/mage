package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandChosenControlledPermanentEffect;
import mage.abilities.effects.common.discard.LookTargetHandChooseDiscardEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author LoneFox
 */
public final class DoomsdaySpecter extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("blue or black creature you control");

    static {
        filter.add(Predicates.or(new ColorPredicate(ObjectColor.BLUE), new ColorPredicate(ObjectColor.BLACK)));
    }

    public DoomsdaySpecter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{B}");
        this.subtype.add(SubType.SPECTER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Doomsday Specter enters the battlefield, return a blue or black creature you control to its owner's hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ReturnToHandChosenControlledPermanentEffect(filter), false));
        // Whenever Doomsday Specter deals combat damage to a player, look at that player's hand and choose a card from it. The player discards that card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new LookTargetHandChooseDiscardEffect(), false, true));
    }

    private DoomsdaySpecter(final DoomsdaySpecter card) {
        super(card);
    }

    @Override
    public DoomsdaySpecter copy() {
        return new DoomsdaySpecter(this);
    }
}
