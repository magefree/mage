package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TarkirDuneshaper extends CardImpl {

    public TarkirDuneshaper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.DOG);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);
        this.secondSideCardClazz = mage.cards.b.BurnishedDunestomper.class;

        // {4}{G/P}: Transform Tarkir Duneshaper. Activate only as a sorcery.
        this.addAbility(new TransformAbility());
        this.addAbility(new ActivateAsSorceryActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl<>("{4}{G/P}")));
    }

    private TarkirDuneshaper(final TarkirDuneshaper card) {
        super(card);
    }

    @Override
    public TarkirDuneshaper copy() {
        return new TarkirDuneshaper(this);
    }
}
