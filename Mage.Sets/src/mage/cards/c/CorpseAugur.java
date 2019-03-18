
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.dynamicvalue.common.CardsInTargetPlayersGraveyardCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreatureCard;
import mage.target.TargetPlayer;

/**
 *
 * @author fireshoes
 */
public final class CorpseAugur extends CardImpl {

    public CorpseAugur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // When Corpse Augur dies, you draw X cards and you lose X life, where X is the number of creature cards in target player's graveyard.
        CardsInTargetPlayersGraveyardCount dynamicValue = new CardsInTargetPlayersGraveyardCount(new FilterCreatureCard("the number of creature cards"));
        Effect effect = new DrawCardSourceControllerEffect(dynamicValue);
        effect.setText("You draw X cards");
        Ability ability = new DiesTriggeredAbility(effect, false);
        effect = new LoseLifeSourceControllerEffect(dynamicValue);
        effect.setText("and you lose X life, where X is the number of creature cards in target player's graveyard");
        ability.addEffect(effect);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public CorpseAugur(final CorpseAugur card) {
        super(card);
    }

    @Override
    public CorpseAugur copy() {
        return new CorpseAugur(this);
    }
}
