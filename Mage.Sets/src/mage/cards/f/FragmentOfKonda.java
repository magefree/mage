package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FragmentOfKonda extends CardImpl {

    public FragmentOfKonda(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);
        this.color.setWhite(true);
        this.nightCard = true;

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // When Fragment of Konda dies, draw a card.
        this.addAbility(new DiesSourceTriggeredAbility(new DrawCardSourceControllerEffect(1)));
    }

    private FragmentOfKonda(final FragmentOfKonda card) {
        super(card);
    }

    @Override
    public FragmentOfKonda copy() {
        return new FragmentOfKonda(this);
    }
}
