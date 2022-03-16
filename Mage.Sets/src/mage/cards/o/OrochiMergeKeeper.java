package mage.cards.o;

import mage.MageInt;
import mage.Mana;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.ModifiedPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OrochiMergeKeeper extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(ModifiedPredicate.instance);
    }

    private static final Condition condition = new SourceMatchesFilterCondition(filter);

    public OrochiMergeKeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());

        // As long as Orochi Merge-Keeper is modified, it has "{T}: Add {G}{G}."
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(new SimpleManaAbility(
                        Zone.BATTLEFIELD, Mana.GreenMana(2), new TapSourceCost()
                )), condition, "as long as {this} is modified, it has \"{T}: Add {G}{G}.\""
        )));
    }

    private OrochiMergeKeeper(final OrochiMergeKeeper card) {
        super(card);
    }

    @Override
    public OrochiMergeKeeper copy() {
        return new OrochiMergeKeeper(this);
    }
}
