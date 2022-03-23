
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetSpell;
import mage.target.common.TargetCardInExile;

/**
 *
 * @author fireshoes
 */
public final class UlamogsNullifier extends CardImpl {

    public UlamogsNullifier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{B}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.PROCESSOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Ulamog's Nullifier enters the battlefield, you may put two cards your opponents own
        // from exile into their owners' graveyards. If you do, counter target spell.
        Ability ability = new EntersBattlefieldTriggeredAbility(new UlamogsNullifierEffect(), true);
        ability.addTarget(new TargetSpell());
        this.addAbility(ability);
    }

    private UlamogsNullifier(final UlamogsNullifier card) {
        super(card);
    }

    @Override
    public UlamogsNullifier copy() {
        return new UlamogsNullifier(this);
    }
}

class UlamogsNullifierEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("cards your opponents own from exile");

    static {
        filter.add(TargetController.OPPONENT.getOwnerPredicate());
    }

    public UlamogsNullifierEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may put two cards your opponents own from exile into their owners' graveyards. If you do, counter target spell.";
    }

    public UlamogsNullifierEffect(final UlamogsNullifierEffect effect) {
        super(effect);
    }

    @Override
    public UlamogsNullifierEffect copy() {
        return new UlamogsNullifierEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Spell spell = game.getStack().getSpell(source.getFirstTarget());
        if (controller != null && spell != null) {
            Target target = new TargetCardInExile(2, 2, filter, null);
            if (target.canChoose(source.getControllerId(), source, game)) {
                if (controller.chooseTarget(outcome, target, source, game)) {
                    Cards cardsToGraveyard = new CardsImpl(target.getTargets());
                    controller.moveCards(cardsToGraveyard, Zone.GRAVEYARD, source, game);
                    game.getStack().counter(source.getFirstTarget(), source, game);
                    return true;
                }
            }
        }
        return false;
    }
}
