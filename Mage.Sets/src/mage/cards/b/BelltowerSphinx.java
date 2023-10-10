package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SourceDealsDamageToThisTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class BelltowerSphinx extends CardImpl {

    public BelltowerSphinx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");
        this.subtype.add(SubType.SPHINX);

        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a source deals damage to Belltower Sphinx, that source's controller mills that many cards.
        this.addAbility(new SourceDealsDamageToThisTriggeredAbility(
                new MillCardsTargetEffect(SavedDamageValue.MANY)
                        .setText("that source's controller mills that many cards")
        ));
    }

    private BelltowerSphinx(final BelltowerSphinx card) {
        super(card);
    }

    @Override
    public BelltowerSphinx copy() {
        return new BelltowerSphinx(this);
    }
}
