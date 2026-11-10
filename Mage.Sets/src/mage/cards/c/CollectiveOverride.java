package mage.cards.c;

import mage.abilities.Mode;
import mage.abilities.condition.common.ControlACommanderCondition;
import mage.abilities.dynamicvalue.common.AttackingCreatureCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.BorgToken;

import java.util.UUID;

/**
 * @author muz
 */
public final class CollectiveOverride extends CardImpl {

    public CollectiveOverride(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}{U}");

        // Choose one. If you control a commander as you cast this spell, you may choose both instead.
        this.getSpellAbility().getModes().setChooseText("Choose one. If you control a commander as you cast this spell, you may choose both instead.");
        this.getSpellAbility().getModes().setMoreCondition(2, ControlACommanderCondition.instance);

        // Create a 1/1 colorless Borg artifact creature token for each attacking creature.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new BorgToken(), new AttackingCreatureCount())
            .setText("create a 1/1 colorless Borg artifact creature token for each attacking creature"));

        // Draw a card for each attacking creature.
        this.getSpellAbility().addMode(new Mode(new DrawCardSourceControllerEffect(new AttackingCreatureCount())
            .setText("draw a card for each attacking creature")));
    }

    private CollectiveOverride(final CollectiveOverride card) {
        super(card);
    }

    @Override
    public CollectiveOverride copy() {
        return new CollectiveOverride(this);
    }
}
