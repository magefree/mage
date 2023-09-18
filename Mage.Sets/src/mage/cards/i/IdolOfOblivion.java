package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.CreatedTokenThisTurnCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.permanent.token.EldraziToken;
import mage.watchers.common.CreatedTokenWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IdolOfOblivion extends CardImpl {

    public IdolOfOblivion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {T}: Draw a card. Activate this ability only if you created a token this turn.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1),
                new TapSourceCost(), CreatedTokenThisTurnCondition.instance
        ).addHint(CreatedTokenThisTurnCondition.getHint()), new CreatedTokenWatcher());

        // {8}, {T}, Sacrifice Idol of Oblivion: Create 10/10 colorless Eldrazi creature token.
        Ability ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new EldraziToken()), new GenericManaCost(8)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private IdolOfOblivion(final IdolOfOblivion card) {
        super(card);
    }

    @Override
    public IdolOfOblivion copy() {
        return new IdolOfOblivion(this);
    }
}
