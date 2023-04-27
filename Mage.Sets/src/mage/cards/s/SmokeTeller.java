
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.card.FaceDownPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class SmokeTeller extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("face down creature");

    static {
        filter.add(FaceDownPredicate.instance);
    }

    public SmokeTeller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // 1U: Look at target face-down creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SmokeTellerLookFaceDownEffect(), new ManaCostsImpl<>("{1}{U}"));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private SmokeTeller(final SmokeTeller card) {
        super(card);
    }

    @Override
    public SmokeTeller copy() {
        return new SmokeTeller(this);
    }
}

class SmokeTellerLookFaceDownEffect extends OneShotEffect {

    public SmokeTellerLookFaceDownEffect() {
        super(Outcome.Benefit);
        this.staticText = "Look at target face-down creature";
    }

    public SmokeTellerLookFaceDownEffect(final SmokeTellerLookFaceDownEffect effect) {
        super(effect);
    }

    @Override
    public SmokeTellerLookFaceDownEffect copy() {
        return new SmokeTellerLookFaceDownEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getObject(source);
        if (player == null || mageObject == null) {
            return false;
        }
        Permanent faceDownCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (faceDownCreature != null) {
            Permanent copyFaceDown = faceDownCreature.copy();
            copyFaceDown.setFaceDown(false, game);
            Cards cards = new CardsImpl(copyFaceDown);
            player.lookAtCards("face down card - " + mageObject.getName(), cards, game);
        } else {
            return false;
        }
        return true;
    }
}