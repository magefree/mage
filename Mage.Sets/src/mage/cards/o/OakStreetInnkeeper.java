package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.NotMyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.hint.common.NotMyTurnHint;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class OakStreetInnkeeper extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("tapped creatures you control");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    public OakStreetInnkeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.ELF);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // As long as it's not your turn, tapped creatures you control have hexproof.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new GainAbilityControlledEffect(HexproofAbility.getInstance(), Duration.WhileOnBattlefield, filter),
                NotMyTurnCondition.instance,
                "As long as it's not your turn, tapped creatures you control have hexproof"))
                .addHint(NotMyTurnHint.instance));

    }

    private OakStreetInnkeeper(final OakStreetInnkeeper card) {
        super(card);
    }

    @Override
    public OakStreetInnkeeper copy() {
        return new OakStreetInnkeeper(this);
    }
}