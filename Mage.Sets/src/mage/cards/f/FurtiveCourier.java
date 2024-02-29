package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SacrificedArtifactThisTurnCondition;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.watchers.common.PermanentsSacrificedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FurtiveCourier extends CardImpl {

    public FurtiveCourier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Furtive Courier can't be blocked as long as you've sacrificed an artifact this turn.
        this.addAbility(new SimpleStaticAbility(new ConditionalRestrictionEffect(
                new CantBeBlockedSourceEffect(), SacrificedArtifactThisTurnCondition.instance,
                "{this} can't be blocked as long as you've sacrificed an artifact this turn"
        )).addHint(SacrificedArtifactThisTurnCondition.getHint()), new PermanentsSacrificedWatcher());

        // Whenever Furtive Courier attacks, draw a card, then discard a card.
        this.addAbility(new AttacksTriggeredAbility(new DrawDiscardControllerEffect(1, 1)));
    }

    private FurtiveCourier(final FurtiveCourier card) {
        super(card);
    }

    @Override
    public FurtiveCourier copy() {
        return new FurtiveCourier(this);
    }
}
