package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAbilityTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyStackObjectEffect;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.FilterStackObject;
import mage.filter.common.FilterActivatedOrTriggeredAbility;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.mageobject.PermanentPredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Map;
import java.util.UUID;

/**
 * @author JayDi85
 */
public final class UnboundFlourishing extends CardImpl {

    private static final FilterSpell filterPermanent = new FilterSpell("a permanent spell with a mana cost that contains {X}");
    private static final FilterSpell filterInstantSorcery = new FilterInstantOrSorcerySpell("an instant or sorcery spell with a mana cost that contains {X}");
    private static final FilterStackObject filterAbility = new FilterActivatedOrTriggeredAbility("an activated ability with an activation cost that contains {X}");

    static {
        filterPermanent.add(PermanentPredicate.instance);
        filterPermanent.add(UnboundFlourishingCostContainsXPredicate.instance);
        filterInstantSorcery.add(UnboundFlourishingCostContainsXPredicate.instance);
        filterAbility.add(UnboundFlourishingCostContainsXPredicate.instance);
    }

    public UnboundFlourishing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // Whenever you cast a permanent spell with a mana cost that contains {X}, double the value of X.
        this.addAbility(new SpellCastControllerTriggeredAbility(new UnboundFlourishingDoubleXEffect(), filterPermanent, false, SetTargetPointer.SPELL));

        // Whenever you cast an instant or sorcery spell or activate an ability,
        // if that spell’s mana cost or that ability’s activation cost contains {X}, copy that spell or ability.
        // You may choose new targets for the copy.
        this.addAbility(new OrTriggeredAbility(Zone.BATTLEFIELD,
                new CopyStackObjectEffect("that spell or ability"), false,
                "Whenever you cast an instant or sorcery spell or activate an ability, " +
                        "if that spell's mana cost or that ability's activation cost contains {X}, ",
                new SpellCastControllerTriggeredAbility(null, filterInstantSorcery, false, SetTargetPointer.SPELL),
                new ActivateAbilityTriggeredAbility(null, filterAbility, SetTargetPointer.SPELL)
        ));
    }

    private UnboundFlourishing(final UnboundFlourishing card) {
        super(card);
    }

    @Override
    public UnboundFlourishing copy() {
        return new UnboundFlourishing(this);
    }
}

enum UnboundFlourishingCostContainsXPredicate implements Predicate<StackObject> {
    instance;

    @Override
    public boolean apply(StackObject input, Game game) {
        if (input instanceof Spell) {
            return ((Spell) input).getSpellAbility().getManaCostsToPay().containsX();
        } else if (input instanceof StackAbility) {
            return input.getStackAbility().getManaCostsToPay().containsX();
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Contains {X}";
    }
}

class UnboundFlourishingDoubleXEffect extends OneShotEffect {

    UnboundFlourishingDoubleXEffect() {
        super(Outcome.Benefit);
        this.staticText = "double the value of X";
    }

    private UnboundFlourishingDoubleXEffect(final UnboundFlourishingDoubleXEffect effect) {
        super(effect);
    }

    @Override
    public UnboundFlourishingDoubleXEffect copy() {
        return new UnboundFlourishingDoubleXEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Player controller = game.getPlayer(source.getControllerId());
        if (player != null && controller != null) {
            Spell needObject = game.getSpell(getTargetPointer().getFirst(game, source));
            if (needObject != null) {
                Map<String, Object> tagsMap = CardUtil.getSourceCostsTagsMap(game, needObject.getSpellAbility());
                if (tagsMap.containsKey("X")) {
                    tagsMap.put("X", ((int) tagsMap.get("X")) * 2);
                }
            }
        }
        return false;
    }
}
