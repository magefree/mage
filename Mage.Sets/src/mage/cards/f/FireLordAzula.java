package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CopyStackObjectEffect;
import mage.abilities.keyword.FirebendingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FireLordAzula extends CardImpl {

    public FireLordAzula(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Firebending 2
        this.addAbility(new FirebendingAbility(2));

        // Whenever you cast a spell while Fire Lord Azula is attacking, copy that spell. You may choose new targets for the copy.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CopyStackObjectEffect("that spell"),
                StaticFilters.FILTER_SPELL_A, false, SetTargetPointer.SPELL
        ));
    }

    private FireLordAzula(final FireLordAzula card) {
        super(card);
    }

    @Override
    public FireLordAzula copy() {
        return new FireLordAzula(this);
    }
}
