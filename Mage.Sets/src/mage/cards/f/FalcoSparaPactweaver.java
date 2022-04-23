package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.RemoveCounterCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FalcoSparaPactweaver extends CardImpl {

    public FalcoSparaPactweaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Falco Spara, Pactweaver enters the battlefield with a shield counter on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.SHIELD.createInstance(1)),
                "with a shield counter on it. <i>(If it would be dealt damage " +
                        "or destroyed, remove a shield counter from it instead.)</i>"
        ));

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));

        // You may cast spells from the top of your library by removing a counter from a creature you control in addition to paying their other costs.
        this.addAbility(new SimpleStaticAbility(new FalcoSparaPactweaverEffect()));
    }

    private FalcoSparaPactweaver(final FalcoSparaPactweaver card) {
        super(card);
    }

    @Override
    public FalcoSparaPactweaver copy() {
        return new FalcoSparaPactweaver(this);
    }
}

class FalcoSparaPactweaverEffect extends AsThoughEffectImpl {

    FalcoSparaPactweaverEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.AIDontUseIt);
        staticText = "you may cast spells from the top of your library by removing " +
                "a counter from a creature you control in addition to paying their other costs";
    }

    private FalcoSparaPactweaverEffect(final FalcoSparaPactweaverEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public FalcoSparaPactweaverEffect copy() {
        return new FalcoSparaPactweaverEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        Card cardToCheck = game.getCard(objectId);
        if (cardToCheck == null
                || !source.isControlledBy(affectedControllerId)
                || !cardToCheck.isOwnedBy(affectedControllerId)) {
            return false;
        }
        Player player = game.getPlayer(cardToCheck.getOwnerId());
        if (player == null) {
            return false;
        }
        Card topCard = player.getLibrary().getFromTop(game);
        if (topCard == null
                || !topCard.getId().equals(cardToCheck.getMainCard().getId())
                || cardToCheck.isLand(game)) {
            return false;
        }

        Costs<Cost> newCosts = new CostsImpl<>();
        newCosts.add(new RemoveCounterCost(new TargetControlledCreaturePermanent()));
        newCosts.addAll(cardToCheck.getSpellAbility().getCosts());
        player.setCastSourceIdWithAlternateMana(cardToCheck.getId(), cardToCheck.getManaCost(), newCosts);
        return true;
    }
}
