package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterPlaneswalkerPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PredatoryWurm extends CardImpl {

    private static final FilterPermanent filter = new FilterPlaneswalkerPermanent(SubType.GARRUK);
    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

    public PredatoryWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.WURM);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Predatory Wurm gets +2/+2 as long as you control a Garruk planeswalker.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield),
                condition, "{this} gets +2/+2 as long as you control a Garruk planeswalker"
        )));
    }

    private PredatoryWurm(final PredatoryWurm card) {
        super(card);
    }

    @Override
    public PredatoryWurm copy() {
        return new PredatoryWurm(this);
    }
}
