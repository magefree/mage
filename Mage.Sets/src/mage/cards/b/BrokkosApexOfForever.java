package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.keyword.MutateAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;

import java.util.UUID;

/**
 * @author NinthWorld
 */
public final class BrokkosApexOfForever extends CardImpl {

    public BrokkosApexOfForever(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}{U}");
        addSuperType(SuperType.LEGENDARY);
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
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new BrokkosMutateFromGraveyardEffect()));
    }

    private BrokkosApexOfForever(final BrokkosApexOfForever card) {
        super(card);
    }

    @Override
    public BrokkosApexOfForever copy() {
        return new BrokkosApexOfForever(this);
    }
}

class BrokkosMutateFromGraveyardEffect extends AsThoughEffectImpl {

    BrokkosMutateFromGraveyardEffect() {
        super(AsThoughEffectType.CAST_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.PutCreatureInPlay);
        staticText = "You may cast {this} from your graveyard using its mutate ability";
    }

    private BrokkosMutateFromGraveyardEffect(final BrokkosMutateFromGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public BrokkosMutateFromGraveyardEffect copy() {
        return new BrokkosMutateFromGraveyardEffect(this);
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
