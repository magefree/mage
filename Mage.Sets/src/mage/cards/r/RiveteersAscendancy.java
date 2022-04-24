package mage.cards.r;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RiveteersAscendancy extends CardImpl {

    private static final FilterCard filter
            = new FilterCreatureCard("creature card with lesser mana value from your graveyard ");

    static {
        filter.add(RiveteersAscendancyPredicate.instance);
    }

    public RiveteersAscendancy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}{R}{G}");

        // Whenever you sacrifice a creature, you may return target creature card with lesser mana value from your graveyard to the battlefield tapped. Do this only once each turn.
        Ability ability = new SacrificePermanentTriggeredAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect(true)
        ).setDoOnlyOnce(true);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private RiveteersAscendancy(final RiveteersAscendancy card) {
        super(card);
    }

    @Override
    public RiveteersAscendancy copy() {
        return new RiveteersAscendancy(this);
    }
}

enum RiveteersAscendancyPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return input
                .getObject()
                .getManaValue()
                < input
                .getSource()
                .getEffects()
                .stream()
                .map(effect -> effect.getValue("sacrificedPermanent"))
                .filter(Objects::nonNull)
                .map(Permanent.class::cast)
                .mapToInt(MageObject::getManaValue)
                .max()
                .orElse(0);
    }
}
