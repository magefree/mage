package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.common.continuous.SetBaseToughnessSourceEffect;
import mage.abilities.hint.common.DomainHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class AvenTrailblazer extends CardImpl {

    public AvenTrailblazer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(0);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Domain - Aven Trailblazer's toughness is equal to the number of basic land types among lands you control.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SetBaseToughnessSourceEffect(DomainValue.REGULAR)
                .setText("{this}'s toughness is equal to the number of basic land types among lands you control")
        ).addHint(DomainHint.instance).setAbilityWord(AbilityWord.DOMAIN));
    }

    private AvenTrailblazer(final AvenTrailblazer card) {
        super(card);
    }

    @Override
    public AvenTrailblazer copy() {
        return new AvenTrailblazer(this);
    }
}
