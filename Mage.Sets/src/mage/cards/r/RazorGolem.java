package mage.cards.r;

import mage.MageInt;
import mage.abilities.keyword.AffinityAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AffinityType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class RazorGolem extends CardImpl {

    public RazorGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Affinity for Plains
        this.addAbility(new AffinityAbility(AffinityType.PLAINS));

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
    }

    private RazorGolem(final RazorGolem card) {
        super(card);
    }

    @Override
    public RazorGolem copy() {
        return new RazorGolem(this);
    }
}
