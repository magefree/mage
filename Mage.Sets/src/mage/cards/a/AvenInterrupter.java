package mage.cards.a;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.SpellsCostIncreasingAllEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.PlotAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.CastFromZonePredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class AvenInterrupter extends CardImpl {

    private static final FilterCard filter = new FilterCard("spells your opponent cast from graveyards or from exile");

    static {
        filter.add(Predicates.or(
                new CastFromZonePredicate(Zone.GRAVEYARD),
                new CastFromZonePredicate(Zone.EXILED)
        ));
    }

    public AvenInterrupter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Aven Interrupter enters the battlefield, exile target spell. It becomes plotted.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AvenInterrupterEffect());
        ability.addTarget(new TargetSpell());
        this.addAbility(ability);

        // Spells your opponents cast from graveyards or from exile cost 2 more to cast.
        this.addAbility(new SimpleStaticAbility(
                new SpellsCostIncreasingAllEffect(2, filter, TargetController.OPPONENT)
        ));
    }

    private AvenInterrupter(final AvenInterrupter card) {
        super(card);
    }

    @Override
    public AvenInterrupter copy() {
        return new AvenInterrupter(this);
    }
}

class AvenInterrupterEffect extends OneShotEffect {

    AvenInterrupterEffect() {
        super(Outcome.Detriment);
        staticText = "exile target spell. It becomes plotted";
    }

    private AvenInterrupterEffect(final AvenInterrupterEffect effect) {
        super(effect);
    }

    @Override
    public AvenInterrupterEffect copy() {
        return new AvenInterrupterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }
        Spell spell = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
        if (spell == null) {
            return false;
        }
        return PlotAbility.doExileAndPlotCard(spell, game, source);
    }
}