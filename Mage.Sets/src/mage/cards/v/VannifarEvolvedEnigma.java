package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.keyword.ManifestEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class VannifarEvolvedEnigma extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("colorless creature you control");

    static {
        filter.add(ColorlessPredicate.instance);
    }

    public VannifarEvolvedEnigma(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.OOZE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // At the beginning of combat on your turn, choose one --
        // * Cloak a card from your hand.
        // * Put a +1/+1 counter on each colorless creature you control.
        Ability ability = new BeginningOfCombatTriggeredAbility(new VannifarCloakAbility(), TargetController.YOU, false);
        ability.addMode(new Mode(new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter)));
        this.addAbility(ability);
    }

    private VannifarEvolvedEnigma(final VannifarEvolvedEnigma card) {
        super(card);
    }

    @Override
    public VannifarEvolvedEnigma copy() {
        return new VannifarEvolvedEnigma(this);
    }
}

class VannifarCloakAbility extends OneShotEffect {
    VannifarCloakAbility() {
        super(Outcome.PutCreatureInPlay);
        staticText = "cloak a card from your hand";
    }

    private VannifarCloakAbility(final VannifarCloakAbility effect) {
        super(effect);
    }

    @Override
    public VannifarCloakAbility copy() {
        return new VannifarCloakAbility(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetCardInHand target = new TargetCardInHand();
        if (controller.choose(Outcome.PutCardInPlay, target, source, game)) {
            Cards cards = new CardsImpl(target.getTargets());
            return !ManifestEffect.doManifestCards(game, source, controller, cards.getCards(game), true).isEmpty();
        }
        return false;
    }
}
