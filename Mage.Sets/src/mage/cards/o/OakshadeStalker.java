package mage.cards.o;

import mage.abilities.common.PayMoreToCastAsThoughtItHadFlashAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
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
public final class OakshadeStalker extends TransformingDoubleFacedCard {

    public OakshadeStalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.RANGER, SubType.WEREWOLF}, "{2}{G}",
                "Moonlit Ambusher",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "G");

        // Oakshade Stalker
        this.getLeftHalfCard().setPT(3, 3);

        // You may cast this spell as though it had flash if you pay {2} more to cast it.
        this.getLeftHalfCard().addAbility(new PayMoreToCastAsThoughtItHadFlashAbility(this.getLeftHalfCard(), new ManaCostsImpl<>("{2}")));

        // Daybound
        this.getLeftHalfCard().addAbility(new DayboundAbility());

        // Moonlit Ambusher
        this.getRightHalfCard().setPT(6, 3);

        // Nightbound
        this.getRightHalfCard().addAbility(new NightboundAbility());
    }

    private OakshadeStalker(final OakshadeStalker card) {
        super(card);
    }

    @Override
    public OakshadeStalker copy() {
        return new OakshadeStalker(this);
    }
}
