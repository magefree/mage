package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.constants.*;
import mage.abilities.keyword.MutateAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;

/**
 *
 * @author freaisdead
 */
public final class BrokkosApexOfForever extends CardImpl {

    public BrokkosApexOfForever(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}{U}");
        
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.BEAST);
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Mutate {2}{U/B}{G}{G}
        this.addAbility(new MutateAbility(this, "{2}{U/B}{G}{G}"));

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // You may cast Brokkos, Apex of Forever from your graveyard using its mutate ability.
        this.addAbility(new SimpleStaticAbility(Zone.GRAVEYARD, new BrokkosApexOfForeverEffect()));
    }

    private BrokkosApexOfForever(final BrokkosApexOfForever card) {
        super(card);
    }

    @Override
    public BrokkosApexOfForever copy() {
        return new BrokkosApexOfForever(this);
    }
}

class BrokkosApexOfForeverEffect extends AsThoughEffectImpl {

    BrokkosApexOfForeverEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.PutCreatureInPlay);
        staticText = "You may cast {this} from your graveyard using its mutate ability";
    }

    private BrokkosApexOfForeverEffect(final BrokkosApexOfForeverEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public BrokkosApexOfForeverEffect copy() {
        return new BrokkosApexOfForeverEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability affectedAbility, Ability source, Game game, UUID playerId) {
        return objectId.equals(source.getSourceId())
                && source.isControlledBy(playerId)
                && affectedAbility instanceof MutateAbility
                && game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD
                && game.getCard(source.getSourceId()) != null;
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        return false;
    }
}
