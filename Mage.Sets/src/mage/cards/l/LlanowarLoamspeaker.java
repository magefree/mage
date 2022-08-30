package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.mana.AnyColorManaAbility;
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
public final class LlanowarLoamspeaker extends CardImpl {

    public LlanowarLoamspeaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // {T}: Target land you control becomes a 3/3 Elemental creature with haste until end of turn. It's still a land. Activate only as as sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new BecomesCreatureTargetEffect(
                new CreatureToken(
                        3, 3, "3/3 Elemental creature with haste", SubType.ELEMENTAL
                ).withAbility(HasteAbility.getInstance()), false, true, Duration.EndOfTurn
        ), new TapSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND));
        this.addAbility(ability);
    }

    private LlanowarLoamspeaker(final LlanowarLoamspeaker card) {
        super(card);
    }

    @Override
    public LlanowarLoamspeaker copy() {
        return new LlanowarLoamspeaker(this);
    }
}
