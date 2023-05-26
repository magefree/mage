package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GorionWiseMentor extends CardImpl {

    private static final FilterSpell filter
            = new FilterSpell("an Adventure spell");

    static {
        filter.add(SubType.ADVENTURE.getPredicate());
    }

    public GorionWiseMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever you cast an Adventure spell, you may copy it. You may choose new targets for the copy.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CopyTargetSpellEffect(true).withSpellName("it"),
                filter, true, true
        ));
    }

    private GorionWiseMentor(final GorionWiseMentor card) {
        super(card);
    }

    @Override
    public GorionWiseMentor copy() {
        return new GorionWiseMentor(this);
    }
}
