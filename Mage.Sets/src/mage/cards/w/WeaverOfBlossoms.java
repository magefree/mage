package mage.cards.w;

import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.NightboundAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WeaverOfBlossoms extends TransformingDoubleFacedCard {

    public WeaverOfBlossoms(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WEREWOLF}, "{2}{G}",
                "Blossom-Clad Werewolf",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "G"
        );

        // Weaver of Blossoms
        this.getLeftHalfCard().setPT(2, 3);

        // {T}: Add one mana of any color.
        this.getLeftHalfCard().addAbility(new AnyColorManaAbility());

        // Daybound
        this.getLeftHalfCard().addAbility(new DayboundAbility());

        // Blossom-Clad Werewolf
        this.getRightHalfCard().setPT(3, 4);

        // {T}: Add two mana of any one color.
        this.getRightHalfCard().addAbility(new SimpleManaAbility(
                Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(2), new TapSourceCost()
        ));

        // Nightbound
        this.getRightHalfCard().addAbility(new NightboundAbility());
    }

    private WeaverOfBlossoms(final WeaverOfBlossoms card) {
        super(card);
    }

    @Override
    public WeaverOfBlossoms copy() {
        return new WeaverOfBlossoms(this);
    }
}
