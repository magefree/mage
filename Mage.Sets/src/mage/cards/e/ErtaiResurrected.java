package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetStackObject;

/**
 *
 * @author weirddan455
 */
public final class ErtaiResurrected extends CardImpl {

    private static final FilterCreatureOrPlaneswalkerPermanent filter =
            new FilterCreatureOrPlaneswalkerPermanent("another creature or planeswalker");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public ErtaiResurrected(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Ertai Resurrected enters the battlefield, choose up to one --
        // * Counter target spell, activated ability, or triggered ability. Its controller draws a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ErtaiResurrectedCounterEffect());
        ability.addTarget(new TargetStackObject());
        ability.getModes().setMinModes(0);
        ability.getModes().setMaxModes(1);

        // * Destroy another target creature or planeswalker. Its controller draws a card.
        Mode mode = new Mode(new ErtaiResurrectedDestroyEffect());
        mode.addTarget(new TargetPermanent(filter));
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private ErtaiResurrected(final ErtaiResurrected card) {
        super(card);
    }

    @Override
    public ErtaiResurrected copy() {
        return new ErtaiResurrected(this);
    }
}

class ErtaiResurrectedCounterEffect extends OneShotEffect {

    public ErtaiResurrectedCounterEffect() {
        super(Outcome.Detriment);
        this.staticText = "Counter target spell, activated ability, or triggered ability. Its controller draws a card.";
    }

    private ErtaiResurrectedCounterEffect(final ErtaiResurrectedCounterEffect effect) {
        super(effect);
    }

    @Override
    public ErtaiResurrectedCounterEffect copy() {
        return new ErtaiResurrectedCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID targetId = source.getFirstTarget();
        StackObject stackObject = game.getStack().getStackObject(targetId);
        if (stackObject == null) {
            return false;
        }
        Player player = game.getPlayer(stackObject.getControllerId());;
        game.getStack().counter(targetId, source, game);
        if (player != null) {
            player.drawCards(1, source, game);
        }
        return true;
    }
}

class ErtaiResurrectedDestroyEffect extends OneShotEffect {

    public ErtaiResurrectedDestroyEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy another target creature or planeswalker. Its controller draws a card.";
    }

    private ErtaiResurrectedDestroyEffect(final ErtaiResurrectedDestroyEffect effect) {
        super(effect);
    }

    @Override
    public ErtaiResurrectedDestroyEffect copy() {
        return new ErtaiResurrectedDestroyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        Player player = game.getPlayer(permanent.getControllerId());
        permanent.destroy(source, game);
        if (player != null) {
            player.drawCards(1, source, game);
        }
        return true;
    }
}
