package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOpponentTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.IxalanVampireToken;
import mage.players.Player;

/**
 * Charismatic Conqueror {1}{W}
 * Creature - Vampire Soldier 2/2
 * Vigilance
 * Whenever an artifact or creature enters the battlefield untapped and under an opponent's control, they may tap that permanent. If they don't, you create a 1/1 white Vampire creature token with lifelink.
 *
 * @author DominionSpy
 */
public final class CharismaticConqueror extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
        filter.add(TappedPredicate.UNTAPPED);
    }

    public CharismaticConqueror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever an artifact or creature enters the battlefield untapped and under an opponent's control, they may tap that permanent. If they don't, you create a 1/1 white Vampire creature token with lifelink.
        Ability ability = new EntersBattlefieldOpponentTriggeredAbility(
                Zone.BATTLEFIELD, new CharismaticConquerorEffect(), filter, false, SetTargetPointer.PERMANENT
        ).setTriggerPhrase("Whenever an artifact or creature enters the battlefield untapped and under an opponent's control, ");
        this.addAbility(ability);
    }

    private CharismaticConqueror(final CharismaticConqueror card) {
        super(card);
    }

    @Override
    public CharismaticConqueror copy() {
        return new CharismaticConqueror(this);
    }
}

class CharismaticConquerorEffect extends OneShotEffect {

    CharismaticConquerorEffect() {
        super(Outcome.Benefit);
        this.staticText = "they may tap that permanent. If they don't, you create a 1/1 white Vampire creature token with lifelink.";
    }

    private CharismaticConquerorEffect(final CharismaticConquerorEffect effect) {
        super(effect);
    }

    @Override
    public CharismaticConquerorEffect copy() {
        return new CharismaticConquerorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null && game.getPlayer(permanent.getControllerId()) != null) {
            Player opponent = game.getPlayer(permanent.getControllerId());
            if (!permanent.isTapped() &&
                    opponent.chooseUse(Outcome.Tap, "Tap " + permanent.getLogName(), source, game)) {
                permanent.tap(source, game);
                return false;
            }
        }

        return new CreateTokenEffect(new IxalanVampireToken()).apply(game, source);
    }
}
