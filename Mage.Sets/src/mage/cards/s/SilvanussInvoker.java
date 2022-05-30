package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SilvanussInvoker extends CardImpl {

    public SilvanussInvoker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Conjure Elemental — {8}: Untap target land you control. It becomes an 8/8 Elemental creature with trample and haste until end of turn. It's still a land.
        Ability ability = new SimpleActivatedAbility(new UntapTargetEffect(), new GenericManaCost(8));
        ability.addEffect(new BecomesCreatureTargetEffect(
                new CreatureToken(8, 8, "8/8 Elemental creature with trample and haste")
                        .withSubType(SubType.ELEMENTAL)
                        .withAbility(TrampleAbility.getInstance())
                        .withAbility(HasteAbility.getInstance()),
                false, true, Duration.EndOfTurn
        ));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND));
        this.addAbility(ability.withFlavorWord("Conjure Elemental"));
    }

    private SilvanussInvoker(final SilvanussInvoker card) {
        super(card);
    }

    @Override
    public SilvanussInvoker copy() {
        return new SilvanussInvoker(this);
    }
}
