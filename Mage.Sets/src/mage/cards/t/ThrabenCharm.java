package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetEnchantmentPermanent;

import java.util.Objects;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class ThrabenCharm extends CardImpl {

    private static final DynamicValue xValue = new MultipliedValue(CreaturesYouControlCount.instance, 2);

    public ThrabenCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Choose one --
        // * Thraben Charm deals damage equal to twice the number of creatures you control to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(xValue)
                .setText("{this} deals damage equal to the number of creatures you control to target creature or planeswalker"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addHint(CreaturesYouControlHint.instance);

        // * Destroy target enchantment.
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetEnchantmentPermanent());
        this.getSpellAbility().addMode(mode);

        // * Exile any number of target players' graveyards.
        mode = new Mode(new ThrabenCharmEffect());
        mode.addTarget(new TargetPlayer(0, Integer.MAX_VALUE, false));
        this.getSpellAbility().addMode(mode);
    }

    private ThrabenCharm(final ThrabenCharm card) {
        super(card);
    }

    @Override
    public ThrabenCharm copy() {
        return new ThrabenCharm(this);
    }
}

class ThrabenCharmEffect extends OneShotEffect {

    ThrabenCharmEffect() {
        super(Outcome.Benefit);
        staticText = "exile any number of target players' graveyards";
    }

    private ThrabenCharmEffect(final ThrabenCharmEffect effect) {
        super(effect);
    }

    @Override
    public ThrabenCharmEffect copy() {
        return new ThrabenCharmEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        this.getTargetPointer()
                .getTargets(game, source)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(Player::getGraveyard)
                .forEach(cards::addAll);
        controller.moveCards(cards, Zone.EXILED, source, game);
        return true;
    }
}
