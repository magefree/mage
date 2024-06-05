package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ModifiedPredicate;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author grimreap124
 */
public final class GoldenTailTrainer extends CardImpl {

    private static final SourcePermanentPowerCount xValue = new SourcePermanentPowerCount(false);
    private static final FilterCard filter = new FilterCard();
    private static final FilterCreaturePermanent modifiedFilter = new FilterCreaturePermanent(
            "modified creatures you control");

    static {
        modifiedFilter.add(ModifiedPredicate.instance);
        filter.add(Predicates.or(SubType.AURA.getPredicate(), SubType.EQUIPMENT.getPredicate()));
    }

    public GoldenTailTrainer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.CREATURE }, "{1}{G}{W}");

        this.subtype.add(SubType.FOX);
        this.subtype.add(SubType.SAMURAI);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Aura and Equipment spells you cast cost {X} less to cast, where X is Golden-Tail Trainer's power.
        this.addAbility(
                new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, xValue)
                        .setText(
                                "Aura and Equipment spells you cast cost {X} less to cast, where X is {this}'s power")));

        // Whenever Golden-Tail Trainer attacks, other modified creatures you control get +X/+X until end of turn, where X is Golden-Tail Trainer's power.
        this.addAbility(
                new AttacksTriggeredAbility(
                        new BoostControlledEffect(xValue, xValue, Duration.EndOfTurn, modifiedFilter, true)));
    }

    private GoldenTailTrainer(final GoldenTailTrainer card) {
        super(card);
    }

    @Override
    public GoldenTailTrainer copy() {
        return new GoldenTailTrainer(this);
    }
}
