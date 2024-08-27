package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RebornHero extends CardImpl {

    public RebornHero(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Threshold - As long as seven or more cards are in your graveyard, Reborn Hero has "When Reborn Hero dies, you may pay {W}{W}. If you do, return Reborn Hero to the battlefield under your control."
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(new DiesSourceTriggeredAbility(new DoIfCostPaid(
                        new ReturnSourceFromGraveyardToBattlefieldEffect(), new ManaCostsImpl<>("{W}{W}")
                ))), ThresholdCondition.instance, "As long as seven or more cards are in " +
                "your graveyard, {this} has \"When {this} dies, you may pay {W}{W}. If you do, " +
                "return {this} to the battlefield under your control.\""
        )).setAbilityWord(AbilityWord.THRESHOLD));
    }

    private RebornHero(final RebornHero card) {
        super(card);
    }

    @Override
    public RebornHero copy() {
        return new RebornHero(this);
    }
}
