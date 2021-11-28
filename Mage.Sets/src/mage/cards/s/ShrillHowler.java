
package mage.cards.s;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesWithLessPowerEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 * @author LevelX2
 */
public final class ShrillHowler extends CardImpl {

    public ShrillHowler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.WEREWOLF);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        this.secondSideCardClazz = mage.cards.h.HowlingChorus.class;

        // Creatures with power less than Shrill Howler's power can't block it.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeBlockedByCreaturesWithLessPowerEffect()));

        // {5}{G}: Transform Shrill Howler.
        this.addAbility(new TransformAbility());
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new TransformSourceEffect(), new ManaCostsImpl<>("{5}{G}")));

    }

    private ShrillHowler(final ShrillHowler card) {
        super(card);
    }

    @Override
    public ShrillHowler copy() {
        return new ShrillHowler(this);
    }
}