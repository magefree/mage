package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.KarnConstructToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DigsiteEngineer extends CardImpl {

    public DigsiteEngineer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you cast an artifact spell, you may pay {2}. If you do, create a 0/0 colorless Construct artifact creature token with "This creature gets +1/+1 for each artifact you control."
        this.addAbility(new SpellCastControllerTriggeredAbility(new DoIfCostPaid(
                new CreateTokenEffect(new KarnConstructToken()), new GenericManaCost(2)
        ), StaticFilters.FILTER_SPELL_AN_ARTIFACT, false));
    }

    private DigsiteEngineer(final DigsiteEngineer card) {
        super(card);
    }

    @Override
    public DigsiteEngineer copy() {
        return new DigsiteEngineer(this);
    }
}
