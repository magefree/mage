package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ConcealingCurtains extends CardImpl {

    public ConcealingCurtains(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);
        this.secondSideCardClazz = mage.cards.r.RevealingEye.class;

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {2}{B}: Transform Concealing Curtains. Activate only as a sorcery.
        this.addAbility(new TransformAbility());
        this.addAbility(new ActivateAsSorceryActivatedAbility(
                new TransformSourceEffect(), new ManaCostsImpl<>("{2}{B}")
        ));
    }

    private ConcealingCurtains(final ConcealingCurtains card) {
        super(card);
    }

    @Override
    public ConcealingCurtains copy() {
        return new ConcealingCurtains(this);
    }
}
