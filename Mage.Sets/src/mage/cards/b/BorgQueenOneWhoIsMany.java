package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.constants.Duration;
import mage.abilities.keyword.LifelinkAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class BorgQueenOneWhoIsMany extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Artifact creatures you control");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public BorgQueenOneWhoIsMany(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BORG);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Artifact creatures you control have lifelink.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
            LifelinkAbility.getInstance(), Duration.WhileOnBattlefield,
            StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE
        )));
    }

    private BorgQueenOneWhoIsMany(final BorgQueenOneWhoIsMany card) {
        super(card);
    }

    @Override
    public BorgQueenOneWhoIsMany copy() {
        return new BorgQueenOneWhoIsMany(this);
    }
}
