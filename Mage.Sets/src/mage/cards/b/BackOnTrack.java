package mage.cards.b;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.PilotSaddleCrewToken;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BackOnTrack extends CardImpl {

    private static final FilterCard filter = new FilterCard("creature or Vehicle card from your graveyard");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
    }

    public BackOnTrack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // Return target creature or Vehicle card from your graveyard to the battlefield. Create a 1/1 colorless Pilot creature token with "This token saddles Mounts and crews Vehicles as though its power were 2 greater."
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addEffect(new CreateTokenEffect(new PilotSaddleCrewToken()));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));
    }

    private BackOnTrack(final BackOnTrack card) {
        super(card);
    }

    @Override
    public BackOnTrack copy() {
        return new BackOnTrack(this);
    }
}
