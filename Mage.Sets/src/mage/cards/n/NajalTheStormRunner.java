package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.CopyNextSpellDelayedTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NajalTheStormRunner extends CardImpl {

    private static final FilterCard filter = new FilterCard("sorcery spells");

    static {
        filter.add(CardType.SORCERY.getPredicate());
    }

    public NajalTheStormRunner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EFREET);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // You may cast sorcery spells as though they had flash.
        this.addAbility(new SimpleStaticAbility(new CastAsThoughItHadFlashAllEffect(Duration.WhileOnBattlefield, filter)));

        // Whenever Najal, the Storm Runner attacks, you may pay {2}. If you do, when you cast your next instant or sorcery spell this turn, copy it. You may choose new targets for the copy.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(
                new CreateDelayedTriggeredAbilityEffect(new CopyNextSpellDelayedTriggeredAbility()), new GenericManaCost(2)
        )));
    }

    private NajalTheStormRunner(final NajalTheStormRunner card) {
        super(card);
    }

    @Override
    public NajalTheStormRunner copy() {
        return new NajalTheStormRunner(this);
    }
}
