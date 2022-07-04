

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class SkithiryxTheBlightDragon extends CardImpl {

    public SkithiryxTheBlightDragon (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.SKELETON);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(InfectAbility.getInstance());
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(HasteAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{B}")));
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{B}{B}")));
    }

    public SkithiryxTheBlightDragon (final SkithiryxTheBlightDragon card) {
        super(card);
    }

    @Override
    public SkithiryxTheBlightDragon copy() {
        return new SkithiryxTheBlightDragon(this);
    }

}
