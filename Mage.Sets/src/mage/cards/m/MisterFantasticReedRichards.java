package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.abilities.common.EntersBattlefieldOneOrMoreTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class MisterFantasticReedRichards extends CardImpl {

    public MisterFantasticReedRichards(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCIENTIST);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever one or more tokens you control enter, you may draw a card.
        this.addAbility(new EntersBattlefieldOneOrMoreTriggeredAbility(
            new DrawCardSourceControllerEffect(1),
            StaticFilters.FILTER_PERMANENT_TOKENS,
            TargetController.YOU,
            true
        ));
    }

    private MisterFantasticReedRichards(final MisterFantasticReedRichards card) {
        super(card);
    }

    @Override
    public MisterFantasticReedRichards copy() {
        return new MisterFantasticReedRichards(this);
    }
}
