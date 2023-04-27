package mage.cards.m;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class MagusOfTheScroll extends CardImpl {

    public MagusOfTheScroll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {3}, {tap}: Name a card. Reveal a card at random from your hand. If it's the named card, Magus of the Scroll deals 2 damage to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.ALL), new ManaCostsImpl<>("{3}"));
        ability.addEffect(new MagusOfTheScrollEffect());
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private MagusOfTheScroll(final MagusOfTheScroll card) {
        super(card);
    }

    @Override
    public MagusOfTheScroll copy() {
        return new MagusOfTheScroll(this);
    }
}

class MagusOfTheScrollEffect extends OneShotEffect {

    public MagusOfTheScrollEffect() {
        super(Outcome.Neutral);
        staticText = ", then reveal a card at random from your hand. If that card has the chosen name, {this} deals 2 damage to any target";
    }

    public MagusOfTheScrollEffect(final MagusOfTheScrollEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        if (sourceObject != null && you != null && cardName != null && !cardName.isEmpty()) {
            if (!you.getHand().isEmpty()) {
                Cards revealed = new CardsImpl();
                Card card = you.getHand().getRandom(game);
                if (card == null) {
                    return false;
                }
                revealed.add(card);
                you.revealCards(sourceObject.getName(), revealed, game);
                if (CardUtil.haveSameNames(card, cardName, game)) {
                    Permanent creature = game.getPermanent(targetPointer.getFirst(game, source));
                    if (creature != null) {
                        creature.damage(2, source.getSourceId(), source, game, false, true);
                        return true;
                    }
                    Player player = game.getPlayer(targetPointer.getFirst(game, source));
                    if (player != null) {
                        player.damage(2, source.getSourceId(), source, game);
                        return true;
                    }
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public MagusOfTheScrollEffect copy() {
        return new MagusOfTheScrollEffect(this);
    }
}
