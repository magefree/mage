package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BlocksOrBlockedSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.BlockingOrBlockedBySourcePredicate;

/**
 *
 * @author L_J
 */
public final class SpittingSlug extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("each creature blocking or blocked by {this}");

    static {
        filter.add(BlockingOrBlockedBySourcePredicate.EITHER);
    }

    public SpittingSlug(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");
        this.subtype.add(SubType.SLUG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever Spitting Slug blocks or becomes blocked, you may pay {1}{G}. If you do, Spitting Slug gains first strike until end of turn. Otherwise, each creature blocking or blocked by Spitting Slug gains first strike until end of turn.
        this.addAbility(new BlocksOrBlockedSourceTriggeredAbility(
            new DoIfCostPaid(
                new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn), 
                new GainAbilityAllEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn, filter), 
                new ManaCostsImpl<>("{1}{G}")).setText("you may pay {1}{G}. If you do, {this} gains first strike until end of turn. Otherwise, each creature blocking or blocked by {this} gains first strike until end of turn"),
            false));
    }

    private SpittingSlug(final SpittingSlug card) {
        super(card);
    }

    @Override
    public SpittingSlug copy() {
        return new SpittingSlug(this);
    }
}
