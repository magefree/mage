
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.TurnedFaceUpAllTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class PineWalker extends CardImpl {

    public PineWalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Morph {4}{G}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{4}{G}")));
        // Whenever Pine Walker or another creature you control is turned face up, untap that creature.
        Effect effect = new UntapTargetEffect();
        effect.setText("untap that creature");
        this.addAbility(new TurnedFaceUpAllTriggeredAbility(effect, new FilterControlledCreaturePermanent("{this} or another creature you control"), true));

    }

    private PineWalker(final PineWalker card) {
        super(card);
    }

    @Override
    public PineWalker copy() {
        return new PineWalker(this);
    }
}
