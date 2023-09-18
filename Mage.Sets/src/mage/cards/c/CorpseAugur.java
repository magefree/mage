package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInTargetPlayersGraveyardCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class CorpseAugur extends CardImpl {

    private static final DynamicValue dynamicValue
            = new CardsInTargetPlayersGraveyardCount(StaticFilters.FILTER_CARD_CREATURE);

    public CorpseAugur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // When Corpse Augur dies, you draw X cards and you lose X life, where X is the number of creature cards in target player's graveyard.
        Ability ability = new DiesSourceTriggeredAbility(
                new DrawCardSourceControllerEffect(dynamicValue).setText("you draw X cards"), false
        );
        ability.addEffect(new LoseLifeSourceControllerEffect(dynamicValue)
                .setText("and you lose X life, where X is the number of creature cards in target player's graveyard"));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private CorpseAugur(final CorpseAugur card) {
        super(card);
    }

    @Override
    public CorpseAugur copy() {
        return new CorpseAugur(this);
    }
}
