package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MockingSprite extends CardImpl {

    private static final FilterCard filter = new FilterInstantOrSorceryCard("Instant and sorcery spells");

    public MockingSprite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Instant and sorcery spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));
    }

    private MockingSprite(final MockingSprite card) {
        super(card);
    }

    @Override
    public MockingSprite copy() {
        return new MockingSprite(this);
    }
}
