package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.ToxicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DuelistOfDeepFaith extends CardImpl {

    public DuelistOfDeepFaith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Toxic 1
        this.addAbility(new ToxicAbility(1));

        // As long as it's your turn, Duelist of Deep Faith has first strike.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FirstStrikeAbility.getInstance()),
                MyTurnCondition.instance, "as long as it's your turn, {this} has first strike"
        )).addHint(MyTurnHint.instance));
    }

    private DuelistOfDeepFaith(final DuelistOfDeepFaith card) {
        super(card);
    }

    @Override
    public DuelistOfDeepFaith copy() {
        return new DuelistOfDeepFaith(this);
    }
}
