package mage.cards.h;

import mage.abilities.keyword.DauntAbility;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.NightboundAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HookhandMariner extends TransformingDoubleFacedCard {

    public HookhandMariner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WEREWOLF}, "{3}{G}",
                "Riphook Raider",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "G"
        );

        // Hookhand Mariner
        this.getLeftHalfCard().setPT(4, 4);

        // Daybound
        this.getLeftHalfCard().addAbility(new DayboundAbility());

        // Riphook Raider
        this.getRightHalfCard().setPT(6, 4);

        // Riphook Raider can't be blocked by creatures with power 2 or less.
        this.getRightHalfCard().addAbility(new DauntAbility());

        // Nightbound
        this.getRightHalfCard().addAbility(new NightboundAbility());
    }

    private HookhandMariner(final HookhandMariner card) {
        super(card);
    }

    @Override
    public HookhandMariner copy() {
        return new HookhandMariner(this);
    }
}
