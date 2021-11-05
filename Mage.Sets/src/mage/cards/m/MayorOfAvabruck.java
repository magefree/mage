package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author North, noxx
 */
public final class MayorOfAvabruck extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Human creatures");

    static {
        filter.add(SubType.HUMAN.getPredicate());
    }

    public MayorOfAvabruck(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.subtype.add(SubType.WEREWOLF);

        this.secondSideCardClazz = mage.cards.h.HowlpackAlpha.class;

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Other Human creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter, true
        )));

        // At the beginning of each upkeep, if no spells were cast last turn, transform Mayor of Avabruck.
        this.addAbility(new TransformAbility());
        this.addAbility(new WerewolfFrontTriggeredAbility());
    }

    private MayorOfAvabruck(final MayorOfAvabruck card) {
        super(card);
    }

    @Override
    public MayorOfAvabruck copy() {
        return new MayorOfAvabruck(this);
    }
}
