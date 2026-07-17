package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.CastNoncreatureSpellThisTurnCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author anonymous
 */
public final class SeekerOfInsight extends CardImpl {

    public SeekerOfInsight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {T}: Draw a card, then discard a card. Activate this ability only if you've cast a noncreature spell this turn.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                new DrawDiscardControllerEffect(), new TapSourceCost(), CastNoncreatureSpellThisTurnCondition.instance
        ).addHint(CastNoncreatureSpellThisTurnCondition.getHint()));
    }

    private SeekerOfInsight(final SeekerOfInsight card) {
        super(card);
    }

    @Override
    public SeekerOfInsight copy() {
        return new SeekerOfInsight(this);
    }
}
