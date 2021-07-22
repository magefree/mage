
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class SoulOfNewPhyrexia extends CardImpl {

    public SoulOfNewPhyrexia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{6}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);
        
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // {5}: Permanents you control gain indestructible until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn), new GenericManaCost(5)));
        // {5}, Exile Soul of New Phyrexia from your graveyard: Permanents you control gain indestructible until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.GRAVEYARD, new GainAbilityControlledEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn), new GenericManaCost(5));
        ability.addCost(new ExileSourceFromGraveCost());
        this.addAbility(ability);
    }

    private SoulOfNewPhyrexia(final SoulOfNewPhyrexia card) {
        super(card);
    }

    @Override
    public SoulOfNewPhyrexia copy() {
        return new SoulOfNewPhyrexia(this);
    }
}
