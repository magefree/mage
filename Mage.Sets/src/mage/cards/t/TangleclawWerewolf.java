
package mage.cards.t;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.combat.CanBlockAdditionalCreatureEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 * @author LevelX2
 */
public final class TangleclawWerewolf extends CardImpl {

    public TangleclawWerewolf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.subtype.add(SubType.WEREWOLF);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        this.secondSideCardClazz = mage.cards.f.FibrousEntangler.class;

        // Tangleclaw Werewolf can block an additional creature each combat.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CanBlockAdditionalCreatureEffect(Duration.WhileOnBattlefield, 1)));

        // {6}{G}: Transform Tangleclaw Werewolf.
        this.addAbility(new TransformAbility());
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new TransformSourceEffect(), new ManaCostsImpl<>("{6}{G}")));
    }

    private TangleclawWerewolf(final TangleclawWerewolf card) {
        super(card);
    }

    @Override
    public TangleclawWerewolf copy() {
        return new TangleclawWerewolf(this);
    }
}
