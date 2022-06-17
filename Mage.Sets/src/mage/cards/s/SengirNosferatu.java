package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.token.SengirNosferatuBatToken;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInExile;

/**
 *
 * @author LoneFox
 *
 */
public final class SengirNosferatu extends CardImpl {

    public SengirNosferatu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // {1}{B}, Exile Sengir Nosferatu: Create a 1/2 black Bat creature token with flying. It has "{1}{B}, Sacrifice this creature: Return an exiled card named Sengir Nosferatu to the battlefield under its owner's control."
        Effect effect = new CreateTokenEffect(new SengirNosferatuBatToken(), 1);
        effect.setText("Create a 1/2 black Bat creature token with flying. It has \"{1}{B}, Sacrifice this creature: Return an exiled card named Sengir Nosferatu to the battlefield under its owner's control.\"");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{1}{B}"));
        ability.addCost(new ExileSourceCost());
        this.addAbility(ability);
    }

    private SengirNosferatu(final SengirNosferatu card) {
        super(card);
    }

    @Override
    public SengirNosferatu copy() {
        return new SengirNosferatu(this);
    }
}

class ReturnSengirNosferatuEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("exiled card named Sengir Nosferatu");

    static {
        filter.add(new NamePredicate("Sengir Nosferatu"));
    }

    public ReturnSengirNosferatuEffect() {
        super(Outcome.Benefit);
    }

    public ReturnSengirNosferatuEffect(final ReturnSengirNosferatuEffect effect) {
        super(effect);
    }

    @Override
    public ReturnSengirNosferatuEffect copy() {
        return new ReturnSengirNosferatuEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Target target = new TargetCardInExile(filter);
        target.setNotTarget(true);
        if (!target.canChoose(controller.getId(), source, game)) {
            return false;
        }
        controller.chooseTarget(Outcome.PutCreatureInPlay, target, source, game);
        Card card = game.getCard(target.getTargets().get(0));
        if (card != null) {
            return controller.moveCards(card, Zone.BATTLEFIELD, source, game);
        }
        return false;
    }
}
