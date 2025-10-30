package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.effects.common.continuous.UntapAllDuringEachOtherPlayersUntapStepEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardSetInfo;
import mage.cards.RoomCard;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class DazzlingTheaterPropRoom extends RoomCard {

    private static final FilterNonlandCard filter = new FilterNonlandCard("creature spells you cast");

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(Predicates.not(new AbilityPredicate(ConvokeAbility.class)));
    }

    public DazzlingTheaterPropRoom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}", "{2}{W}", SpellAbilityType.SPLIT);
        this.subtype.add(SubType.ROOM);

        // Dazzling Theater: Creature spells you cast have convoke.
        Ability left = new SimpleStaticAbility(new GainAbilityControlledSpellsEffect(new ConvokeAbility(), filter));

        // Prop Room: Untap each creature you control during each other player's untap step.
        Ability right = new SimpleStaticAbility(new UntapAllDuringEachOtherPlayersUntapStepEffect(StaticFilters.FILTER_CONTROLLED_CREATURES));

        this.addRoomAbilities(left, right);
    }

    private DazzlingTheaterPropRoom(final DazzlingTheaterPropRoom card) {
        super(card);
    }

    @Override
    public DazzlingTheaterPropRoom copy() {
        return new DazzlingTheaterPropRoom(this);
    }
}
