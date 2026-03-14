package mage.cards.w;

import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.NightboundAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WearyPrisoner extends TransformingDoubleFacedCard {

    public WearyPrisoner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WEREWOLF}, "{3}{R}",
                "Wrathful Jailbreaker",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "R"
        );

        // Weary Prisoner
        this.getLeftHalfCard().setPT(2, 6);

        // Defender
        this.getLeftHalfCard().addAbility(DefenderAbility.getInstance());

        // Daybound
        this.getLeftHalfCard().addAbility(new DayboundAbility());

        // Wrathful Jailbreaker
        this.getRightHalfCard().setPT(6, 6);

        // Wrathful Jailbreaker attacks each combat if able.
        this.getRightHalfCard().addAbility(new AttacksEachCombatStaticAbility());

        // Nightbound
        this.getRightHalfCard().addAbility(new NightboundAbility());
    }

    private WearyPrisoner(final WearyPrisoner card) {
        super(card);
    }

    @Override
    public WearyPrisoner copy() {
        return new WearyPrisoner(this);
    }
}
