package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;

import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KadenasSilencer extends CardImpl {

    public KadenasSilencer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.NAGA);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Kadena's Silencer is turned face up, counter all abilities your opponents control.
        this.addAbility(new TurnedFaceUpSourceTriggeredAbility(new KadenasSilencerEffect()));

        // Megamorph {1}{U}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{1}{U}"), true));
    }

    private KadenasSilencer(final KadenasSilencer card) {
        super(card);
    }

    @Override
    public KadenasSilencer copy() {
        return new KadenasSilencer(this);
    }
}

class KadenasSilencerEffect extends OneShotEffect {

    KadenasSilencerEffect() {
        super(Outcome.Benefit);
        staticText = "counter all abilities your opponents control.";
    }

    private KadenasSilencerEffect(final KadenasSilencerEffect effect) {
        super(effect);
    }

    @Override
    public KadenasSilencerEffect copy() {
        return new KadenasSilencerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<UUID> opps = game.getOpponents(source.getControllerId());
        game.getStack()
                .stream()
                .filter(Ability.class::isInstance)
                .filter(stackObject -> opps.contains(stackObject.getControllerId()))
                .forEach(stackObject -> game.getStack().counter(stackObject.getId(), source, game));
        return true;
    }
}