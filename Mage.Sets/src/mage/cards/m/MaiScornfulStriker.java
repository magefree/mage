package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
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
public final class MaiScornfulStriker extends CardImpl {

    public MaiScornfulStriker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever a player casts a noncreature spell, they lose 2 life.
        this.addAbility(new SpellCastAllTriggeredAbility(
                new LoseLifeTargetEffect(2).setText("they lose 2 life"),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false, SetTargetPointer.PLAYER
        ));
    }

    private MaiScornfulStriker(final MaiScornfulStriker card) {
        super(card);
    }

    @Override
    public MaiScornfulStriker copy() {
        return new MaiScornfulStriker(this);
    }
}
