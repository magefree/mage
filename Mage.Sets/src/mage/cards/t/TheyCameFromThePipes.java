package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.ManifestDreadEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.card.FaceDownPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheyCameFromThePipes extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("a face-down creature you control");

    static {
        filter.add(FaceDownPredicate.instance);
    }

    public TheyCameFromThePipes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{U}");

        // When They Came from the Pipes enters, manifest dread twice.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ManifestDreadEffect());
        ability.addEffect(new ManifestDreadEffect().setText("twice"));
        this.addAbility(ability);

        // Whenever a face-down creature you control enters, draw a card.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(new DrawCardSourceControllerEffect(1), filter));
    }

    private TheyCameFromThePipes(final TheyCameFromThePipes card) {
        super(card);
    }

    @Override
    public TheyCameFromThePipes copy() {
        return new TheyCameFromThePipes(this);
    }
}
