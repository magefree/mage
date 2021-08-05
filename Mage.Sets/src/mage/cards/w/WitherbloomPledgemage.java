package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.MagecraftAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WitherbloomPledgemage extends CardImpl {

    public WitherbloomPledgemage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B/G}{B/G}");

        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Magecraft — Whenever you cast or copy an instant or sorcery spell, you gain 1 life.
        this.addAbility(new MagecraftAbility(new GainLifeEffect(1)));
    }

    private WitherbloomPledgemage(final WitherbloomPledgemage card) {
        super(card);
    }

    @Override
    public WitherbloomPledgemage copy() {
        return new WitherbloomPledgemage(this);
    }
}
