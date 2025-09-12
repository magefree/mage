package mage.cards.a;

import mage.abilities.dynamicvalue.common.DifferentlyNamedPermanentCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.PlantToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AudienceWithTrostani extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("creature tokens you control");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    private static final DifferentlyNamedPermanentCount xValue = new DifferentlyNamedPermanentCount(filter);

    public AudienceWithTrostani(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Create a 0/1 green Plant creature token, then draw cards equal to the number of differently named creature tokens you control.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new PlantToken()));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(xValue)
                .setText(", then draw cards equal to the number of differently named creature tokens you control"));
        this.getSpellAbility().addHint(xValue.getHint());
    }

    private AudienceWithTrostani(final AudienceWithTrostani card) {
        super(card);
    }

    @Override
    public AudienceWithTrostani copy() {
        return new AudienceWithTrostani(this);
    }
}
