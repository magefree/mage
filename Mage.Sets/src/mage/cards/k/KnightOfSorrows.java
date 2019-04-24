package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CanBlockAdditionalCreatureEffect;
import mage.abilities.keyword.AfterlifeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KnightOfSorrows extends CardImpl {

    public KnightOfSorrows(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Knight of Sorrows can block an additional creature each combat.
        this.addAbility(new SimpleStaticAbility(new CanBlockAdditionalCreatureEffect()));

        // Afterlife 1
        this.addAbility(new AfterlifeAbility(1));
    }

    private KnightOfSorrows(final KnightOfSorrows card) {
        super(card);
    }

    @Override
    public KnightOfSorrows copy() {
        return new KnightOfSorrows(this);
    }
}
