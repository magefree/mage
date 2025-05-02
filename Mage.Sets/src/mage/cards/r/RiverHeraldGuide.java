package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.ExploreSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RiverHeraldGuide extends CardImpl {

    public RiverHeraldGuide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When River Herald Guide enters the battlefield, it explores.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ExploreSourceEffect()));
    }

    private RiverHeraldGuide(final RiverHeraldGuide card) {
        super(card);
    }

    @Override
    public RiverHeraldGuide copy() {
        return new RiverHeraldGuide(this);
    }
}
