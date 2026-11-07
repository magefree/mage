package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author muz
 */
public final class SamKirkXenoanthropologist extends CardImpl {

    public SamKirkXenoanthropologist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever an opponent casts a creature spell, investigate.
        this.addAbility(new SpellCastOpponentTriggeredAbility(
            new InvestigateEffect(),
            StaticFilters.FILTER_SPELL_A_CREATURE,
            false
        ));
    }

    private SamKirkXenoanthropologist(final SamKirkXenoanthropologist card) {
        super(card);
    }

    @Override
    public SamKirkXenoanthropologist copy() {
        return new SamKirkXenoanthropologist(this);
    }
}
