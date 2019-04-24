
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.CreatureEntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.SnakeToken;

/**
 *
 * @author LevelX2
 */
public final class SosukesSummons extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nontoken Snake");

    static {
            filter.add(new SubtypePredicate(SubType.SNAKE));
            filter.add(Predicates.not(new TokenPredicate()));
    }

    public SosukesSummons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{G}");


        // Create two 1/1 green Snake creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SnakeToken(), 2));

        // Whenever a nontoken Snake enters the battlefield under your control, you may return Sosuke's Summons from your graveyard to your hand.
        this.addAbility(new CreatureEntersBattlefieldTriggeredAbility(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(), filter, true, false));


    }

    public SosukesSummons(final SosukesSummons card) {
        super(card);
    }

    @Override
    public SosukesSummons copy() {
        return new SosukesSummons(this);
    }
}
