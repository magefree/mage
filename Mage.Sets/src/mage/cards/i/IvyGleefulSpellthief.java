package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.filter.predicate.other.HasOnlySingleTargetPermanentPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.util.functions.StackObjectCopyApplier;

import java.util.Arrays;
import java.util.Iterator;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IvyGleefulSpellthief extends CardImpl {

    private static final FilterSpell filter
            = new FilterSpell("a spell that targets only a single creature other than {this}");

    static {
        filter.add(new HasOnlySingleTargetPermanentPredicate(StaticFilters.FILTER_ANOTHER_CREATURE));
    }

    public IvyGleefulSpellthief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a player casts a spell that targets only a single creature other than Ivy, Gleeful Spellthief, you may copy that spell. The copy targets Ivy.
        this.addAbility(new SpellCastAllTriggeredAbility(new IvyGleefulSpellthiefEffect(), filter, true));
    }

    private IvyGleefulSpellthief(final IvyGleefulSpellthief card) {
        super(card);
    }

    @Override
    public IvyGleefulSpellthief copy() {
        return new IvyGleefulSpellthief(this);
    }
}

class IvyGleefulSpellthiefEffect extends OneShotEffect {

    private static final class IvyGleefulSpellthiefApplier implements StackObjectCopyApplier {

        private final Iterator<MageObjectReferencePredicate> predicate;

        IvyGleefulSpellthiefApplier(Permanent permanent, Game game) {
            this.predicate = Arrays.asList(new MageObjectReferencePredicate(permanent, game)).iterator();
        }

        @Override
        public void modifySpell(StackObject stackObject, Game game) {
        }

        @Override
        public MageObjectReferencePredicate getNextNewTargetType() {
            if (predicate.hasNext()) {
                return predicate.next();
            }
            return null;
        }
    }

    IvyGleefulSpellthiefEffect() {
        super(Outcome.Benefit);
        staticText = "copy that spell. The copy targets {this}";
    }

    private IvyGleefulSpellthiefEffect(final IvyGleefulSpellthiefEffect effect) {
        super(effect);
    }

    @Override
    public IvyGleefulSpellthiefEffect copy() {
        return new IvyGleefulSpellthiefEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell) getValue("spellCast");
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (spell == null || permanent == null) {
            return false;
        }
        spell.createCopyOnStack(
                game, source, source.getControllerId(), false,
                1, new IvyGleefulSpellthiefApplier(permanent, game)
        );
        return true;
    }
}
