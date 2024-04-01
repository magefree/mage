package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TinybonesJoinsUp extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("legendary creature");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public TinybonesJoinsUp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}");

        this.supertype.add(SuperType.LEGENDARY);

        // When Tinybones Joins Up enters the battlefield, any number of target players each discard a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DiscardTargetEffect(1));
        ability.addTarget(new TargetPlayer(0, Integer.MAX_VALUE, false));
        this.addAbility(ability);

        // Whenever a legendary creature enters the battlefield under your control, any number of target players each mill a card and lose 1 life.
        ability = new EntersBattlefieldControlledTriggeredAbility(
                new MillCardsTargetEffect(1), filter
        );
        ability.addEffect(new LoseLifeTargetEffect(1));
        ability.addTarget(new TargetPlayer(0, Integer.MAX_VALUE, false));
        this.addAbility(ability);
    }

    private TinybonesJoinsUp(final TinybonesJoinsUp card) {
        super(card);
    }

    @Override
    public TinybonesJoinsUp copy() {
        return new TinybonesJoinsUp(this);
    }
}
