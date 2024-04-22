package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreatureSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.util.functions.StackObjectCopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TawnosTheToymaker extends CardImpl {

    private static final FilterSpell filter = new FilterCreatureSpell("a Beast or Bird creature spell");

    static {
        filter.add(Predicates.or(
                SubType.BEAST.getPredicate(),
                SubType.BIRD.getPredicate()
        ));
    }

    public TawnosTheToymaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Whenever you cast a Beast or Bird creature spell, you may copy it, except it's an artifact in addition to its other types.
        this.addAbility(new SpellCastControllerTriggeredAbility(new TawnosTheToymakerEffect(), filter, true));
    }

    private TawnosTheToymaker(final TawnosTheToymaker card) {
        super(card);
    }

    @Override
    public TawnosTheToymaker copy() {
        return new TawnosTheToymaker(this);
    }
}

class TawnosTheToymakerEffect extends OneShotEffect {

    TawnosTheToymakerEffect() {
        super(Outcome.Benefit);
        staticText = "copy it, except the copy is an artifact in addition to its other types";
    }

    private TawnosTheToymakerEffect(final TawnosTheToymakerEffect effect) {
        super(effect);
    }

    @Override
    public TawnosTheToymakerEffect copy() {
        return new TawnosTheToymakerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Spell spell = (Spell) getValue("spellCast");
        if (controller == null || spell == null) {
            return false;
        }
        // Create a token copy
        spell.createCopyOnStack(
                game, source, controller.getId(), false,
                1, TawnosTheToymakerApplier.instance
        );
        return true;
    }
}

enum TawnosTheToymakerApplier implements StackObjectCopyApplier {
    instance;

    @Override
    public void modifySpell(StackObject copiedSpell, Game game) {
        copiedSpell.addCardType(CardType.ARTIFACT);
    }

    @Override
    public MageObjectReferencePredicate getNextNewTargetType() {
        return null;
    }
}
