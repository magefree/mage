package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.keyword.BlitzAbility;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SabinMasterMonk extends CardImpl {

    public SabinMasterMonk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Blitz--{2}{R}{R}, Discard a card.
        Ability ability = new BlitzAbility(this, "{2}{R}{R}");
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);

        // You may cast this card from your graveyard using its blitz ability.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SabinMasterMonkEffect()));
    }

    private SabinMasterMonk(final SabinMasterMonk card) {
        super(card);
    }

    @Override
    public SabinMasterMonk copy() {
        return new SabinMasterMonk(this);
    }
}

class SabinMasterMonkEffect extends AsThoughEffectImpl {

    SabinMasterMonkEffect() {
        super(AsThoughEffectType.CAST_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.PutCreatureInPlay);
        staticText = "you may cast this card from your graveyard using its blitz ability";
    }

    private SabinMasterMonkEffect(final SabinMasterMonkEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public SabinMasterMonkEffect copy() {
        return new SabinMasterMonkEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability affectedAbility, Ability source, Game game, UUID playerId) {
        return objectId.equals(source.getSourceId())
                && source.isControlledBy(playerId)
                && affectedAbility instanceof BlitzAbility
                && game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD
                && game.getCard(source.getSourceId()) != null;
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        return false;
    }
}
