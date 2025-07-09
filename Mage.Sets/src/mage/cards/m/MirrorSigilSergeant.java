package mage.cards.m;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class MirrorSigilSergeant extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("you control a blue permanent");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

    public MirrorSigilSergeant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");
        this.subtype.add(SubType.RHINO);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of your upkeep, if you control a blue permanent, you may create a token that's a copy of Mirror-Sigil Sergeant.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new CreateTokenCopySourceEffect(), true
        ).withInterveningIf(condition));
    }

    private MirrorSigilSergeant(final MirrorSigilSergeant card) {
        super(card);
    }

    @Override
    public MirrorSigilSergeant copy() {
        return new MirrorSigilSergeant(this);
    }
}
