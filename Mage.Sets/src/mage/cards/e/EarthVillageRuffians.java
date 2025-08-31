package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.keyword.EarthbendTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetControlledLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EarthVillageRuffians extends CardImpl {

    public EarthVillageRuffians(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B/G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When this creature dies, earthbend 2.
        Ability ability = new DiesSourceTriggeredAbility(new EarthbendTargetEffect(2));
        ability.addTarget(new TargetControlledLandPermanent());
        this.addAbility(ability);
    }

    private EarthVillageRuffians(final EarthVillageRuffians card) {
        super(card);
    }

    @Override
    public EarthVillageRuffians copy() {
        return new EarthVillageRuffians(this);
    }
}
