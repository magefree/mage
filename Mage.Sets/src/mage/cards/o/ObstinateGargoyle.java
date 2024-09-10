package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.PersistAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.ModifiedPredicate;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ObstinateGargoyle extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(ModifiedPredicate.instance);
    }

    private static final Condition condition =
            new SourceMatchesFilterCondition("it's modified", filter);

    public ObstinateGargoyle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{W}{B}");

        this.subtype.add(SubType.GARGOYLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Obstinate Gargoyle has flying as long as it's modified.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield),
                condition, "{this} has flying as long as it's modified"
        )));

        // Persist
        this.addAbility(new PersistAbility());

    }

    private ObstinateGargoyle(final ObstinateGargoyle card) {
        super(card);
    }

    @Override
    public ObstinateGargoyle copy() {
        return new ObstinateGargoyle(this);
    }
}
