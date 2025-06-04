package mage.cards.s;

import mage.MageInt;
import mage.abilities.keyword.AffinityAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AffinityType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkyBlessedSamurai extends CardImpl {

    public SkyBlessedSamurai(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{6}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Affinity for enchantments
        this.addAbility(new AffinityAbility(AffinityType.ENCHANTMENTS));

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private SkyBlessedSamurai(final SkyBlessedSamurai card) {
        super(card);
    }

    @Override
    public SkyBlessedSamurai copy() {
        return new SkyBlessedSamurai(this);
    }
}
