
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RevealTargetPlayerLibraryEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.TargetPlayer;

/**
 *
 * @author cbt33
 */
public final class AvenWindreader extends CardImpl {

    public AvenWindreader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {1}{U}: Target player reveals the top card of their library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RevealTargetPlayerLibraryEffect(1), new ManaCostsImpl("{1}{U}"));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public AvenWindreader(final AvenWindreader card) {
        super(card);
    }

    @Override
    public AvenWindreader copy() {
        return new AvenWindreader(this);
    }
}
