package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.SourceBecomesTargetTriggeredAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PhantasmalDreadmaw extends CardImpl {

    public PhantasmalDreadmaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.subtype.add(SubType.DINOSAUR);
        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Phantasmal Dreadmaw becomes the target of a spell or ability, sacrifice it.
        this.addAbility(new SourceBecomesTargetTriggeredAbility(new SacrificeSourceEffect().setText("sacrifice it")));
    }

    private PhantasmalDreadmaw(final PhantasmalDreadmaw card) {
        super(card);
    }

    @Override
    public PhantasmalDreadmaw copy() {
        return new PhantasmalDreadmaw(this);
    }
}
