package mage.cards.l;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LindenTheSteadfastQueen extends CardImpl {

    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent("white creature you control");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public LindenTheSteadfastQueen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever a white creature you control attacks, you gain 1 life.
        this.addAbility(new AttacksCreatureYouControlTriggeredAbility(new GainLifeEffect(1), false, filter));
    }

    private LindenTheSteadfastQueen(final LindenTheSteadfastQueen card) {
        super(card);
    }

    @Override
    public LindenTheSteadfastQueen copy() {
        return new LindenTheSteadfastQueen(this);
    }
}
