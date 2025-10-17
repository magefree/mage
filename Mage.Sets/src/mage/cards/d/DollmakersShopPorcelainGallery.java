package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.AttacksPlayerWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessAllEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardSetInfo;
import mage.cards.RoomCard;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.ToyToken;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class DollmakersShopPorcelainGallery extends RoomCard {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("non-Toy creatures you control");

    static {
        filter.add(Predicates.not(SubType.TOY.getPredicate()));
    }

    public DollmakersShopPorcelainGallery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}", "{4}{W}{W}", SpellAbilityType.SPLIT);
        this.subtype.add(SubType.ROOM);

        // Dollmaker's Shop: Whenever one or more non-Toy creatures you control attack a player, create a 1/1 white Toy artifact creature token.
        Ability left = new AttacksPlayerWithCreaturesTriggeredAbility(new CreateTokenEffect(new ToyToken()), filter, SetTargetPointer.NONE);

        // Porcelain Gallery: Creatures you control have base power and toughness each equal to the number of creatures you control.
        Ability right = new SimpleStaticAbility(new SetBasePowerToughnessAllEffect(
                CreaturesYouControlCount.PLURAL, Duration.WhileOnBattlefield, StaticFilters.FILTER_CONTROLLED_CREATURES
        ).setText("Creatures you control have base power and toughness each equal to the number of creatures you control"));

        this.addRoomAbilities(left, right.addHint(new ValueHint("Creatures you control", CreaturesYouControlCount.PLURAL)));
    }

    private DollmakersShopPorcelainGallery (final DollmakersShopPorcelainGallery card) {
        super(card);
    }

    @Override
    public DollmakersShopPorcelainGallery copy() {
        return new DollmakersShopPorcelainGallery(this);
    }
}
