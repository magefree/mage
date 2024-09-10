package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SeraphOfNewCapenna extends CardImpl {

    public SeraphOfNewCapenna(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.secondSideCardClazz = mage.cards.s.SeraphOfNewPhyrexia.class;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {4}{B/P}: Transform Seraph of New Capenna. Activate only as a sorcery.
        this.addAbility(new TransformAbility());
        this.addAbility(new ActivateAsSorceryActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl<>("{4}{B/P}")));
    }

    private SeraphOfNewCapenna(final SeraphOfNewCapenna card) {
        super(card);
    }

    @Override
    public SeraphOfNewCapenna copy() {
        return new SeraphOfNewCapenna(this);
    }
}
