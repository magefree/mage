package mage.cards.u;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UlvenwaldOddity extends CardImpl {

    public UlvenwaldOddity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.secondSideCardClazz = mage.cards.u.UlvenwaldBehemoth.class;

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {5}{G}{G}: Transform Ulvenwald Oddity.
        this.addAbility(new TransformAbility());
        this.addAbility(new SimpleActivatedAbility(
                new TransformSourceEffect(), new ManaCostsImpl<>("{5}{G}{G}")
        ));
    }

    private UlvenwaldOddity(final UlvenwaldOddity card) {
        super(card);
    }

    @Override
    public UlvenwaldOddity copy() {
        return new UlvenwaldOddity(this);
    }
}
