package mage.cards.h;

import mage.ConditionalMana;
import mage.MageInt;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.CreatureCastConditionalMana;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HumbleNaturalist extends CardImpl {

    public HumbleNaturalist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {T}: Add one mana of any color. Spend this mana only to cast a creature spell.
        this.addAbility(new ConditionalAnyColorManaAbility(1, new HumbleNaturalistManaBuilder()));
    }

    private HumbleNaturalist(final HumbleNaturalist card) {
        super(card);
    }

    @Override
    public HumbleNaturalist copy() {
        return new HumbleNaturalist(this);
    }
}

class HumbleNaturalistManaBuilder extends ConditionalManaBuilder {
    @Override
    public ConditionalMana build(Object... options) {
        return new CreatureCastConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast a creature spell";
    }
}
