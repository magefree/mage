
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesColorTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.common.TargetSpellOrPermanent;

/**
 *
 * @author LoneFox

 */
public final class BlindSeer extends CardImpl {

    public BlindSeer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {1}{U}: Target spell or permanent becomes the color of your choice until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesColorTargetEffect(Duration.EndOfTurn),
            new ManaCostsImpl<>("{1}{U}"));
        ability.addTarget(new TargetSpellOrPermanent());
        this.addAbility(ability);
    }

    private BlindSeer(final BlindSeer card) {
        super(card);
    }

    @Override
    public BlindSeer copy() {
        return new BlindSeer(this);
    }
}
